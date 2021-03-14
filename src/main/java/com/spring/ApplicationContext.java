package com.spring;

import lombok.Getter;
import lombok.SneakyThrows;
import org.reflections.Reflections;

public class ApplicationContext {
  @Getter private Reflections scanner;
  private JavaConfig config;
  private ObjectFactory factory;
  private Class clazz;

  @SneakyThrows
  public ApplicationContext(Class clazz) {
    this.clazz = clazz;
    String packageNameToScan = getPackageNameToScan();
    this.scanner = new Reflections(packageNameToScan);
    this.config = new JavaConfig(scanner);
    this.factory = new ObjectFactory(this);
  }

  private String getPackageNameToScan() {
    ComponentScan componentScan = (ComponentScan) clazz.getAnnotation(ComponentScan.class);
    if (componentScan != null) {
      return componentScan.name();
    }
    return "";
  }

  @SneakyThrows
  public <T> T getObject(Class<T> type) {
    if (ServiceRegistry.getInstance().getServices().containsKey(type)) {
      return (T) ServiceRegistry.getInstance().getService(type);
    }

    Class<T> implClass = resolveImpl(type);
    T t = factory.createObject(implClass);

    Scope scope = implClass.getAnnotation(Scope.class);
    if (scope == null || "singleton".equals(scope.name().toLowerCase())) {
      ServiceRegistry.getInstance().register(type, t);
    }

    return t;
  }

  private <T> Class<T> resolveImpl(Class<T> bean) {
    if (bean.isInterface()) {
      bean = (Class<T>) config.getImplClass(bean);
    }
    return bean;
  }
}
