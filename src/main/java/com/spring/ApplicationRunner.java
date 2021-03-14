package com.spring;

public class ApplicationRunner {
  public static ApplicationContext run(Class clazz) {
    return new ApplicationContext(clazz);
  }
}
