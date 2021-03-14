package com.spring;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@NoArgsConstructor
public class ServiceRegistry {

  private static final ServiceRegistry INSTANCE = new ServiceRegistry();

  public static ServiceRegistry getInstance() {
    return INSTANCE;
  }

  @Getter private final ConcurrentMap<Class, Object> services = new ConcurrentHashMap<>();

  public void register(Class clazz, Object obj) {
    System.out.println("Register service with name: " + clazz.getName());
    services.put(clazz, obj);
  }

  public Object getService(Class clazz) {
    return services.get(clazz);
  }
}
