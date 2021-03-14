package com.spring;

public interface Config {
  <T> Class<? extends T> getImplClass(Class<T> bean);
}
