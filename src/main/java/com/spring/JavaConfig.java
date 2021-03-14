package com.spring;

import lombok.SneakyThrows;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class JavaConfig implements  Config{

  private Reflections scanner;
  private Map<Class, Class> ifc2ImplClass = new HashMap<>();

  public JavaConfig(Reflections scanner) {
    this.scanner = scanner;
  }


  @Override
  @SneakyThrows
  public <T> Class<? extends T> getImplClass(Class<T> bean) {
    return ifc2ImplClass.computeIfAbsent(bean, aClass -> {
      Set<Class<? extends T>> set = scanner.getSubTypesOf(bean);
      if (set.size() != 1) {
        throw new IllegalArgumentException(bean + " has 0 or more than one impl");
      }
      return set.iterator().next();
    });


  }
}
