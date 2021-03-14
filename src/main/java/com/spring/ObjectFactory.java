package com.spring;

import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
public class ObjectFactory {
  private ApplicationContext context;
  private List<ObjectConfigurator> configurators = new ArrayList<>();

  @SneakyThrows
  public ObjectFactory(ApplicationContext context) {
    this.context = context;
    Set<Class<? extends ObjectConfigurator>> classes =
        context.getScanner().getSubTypesOf(ObjectConfigurator.class);
    for (Class<? extends ObjectConfigurator> aClass : classes) {
      this.configurators.add(aClass.getDeclaredConstructor().newInstance());
    }
  }

  public <T> T createObject(Class<T> type) throws Exception {

    T t = type.getDeclaredConstructor().newInstance();
    configure(t);

    invokeInit(type, t);

    if (type.isAnnotationPresent(Deprecated.class)) {
      InvocationHandler handler =
          (Object proxy, Method method, Object[] args) -> {
            System.out.println("Deprecated class is ==> " + t.getClass());
            return method.invoke(t, args);
          };
      return (T)
          Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), type.getInterfaces(), handler);
    }

    return t;
  }

  private <T> void invokeInit(Class<T> type, T t)
      throws IllegalAccessException, InvocationTargetException {
    for (Method method : type.getMethods()) {
      if (method.isAnnotationPresent(PostConstruct.class)) {
        method.invoke(t);
      }
    }
  }

  private <T> void configure(T t) {
    configurators.forEach(configurator -> configurator.configure(t, context));
  }
}
