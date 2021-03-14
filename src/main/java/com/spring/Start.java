package com.spring;

import lombok.SneakyThrows;

@ComponentScan(name = "com.spring")
public class Start {
  @SneakyThrows
  public static void main(String[] args) {
    ApplicationContext context = ApplicationRunner.run(Start.class);
    context.getObject(GameRegister.class).start();
  }
}
