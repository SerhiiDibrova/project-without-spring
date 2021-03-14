package com.spring;

import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class ValueAnnotationObjectConfigurator implements ObjectConfigurator {

  private Map<String, String> map;

  @SneakyThrows
  public ValueAnnotationObjectConfigurator() {
    String path =
        ClassLoader.getSystemClassLoader().getResource("application.properties").getPath();
    Stream<String> lines = new BufferedReader(new FileReader(path)).lines();
    map = lines.map(line -> line.split("=")).collect(toMap(arr -> arr[0], arr -> arr[1]));
  }

  @Override
  @SneakyThrows
  public void configure(Object t, ApplicationContext context) {
    Class<?> type = t.getClass();
    for (Field field : type.getDeclaredFields()) {
      setFieldValue(t, field);
    }
  }

  private void setFieldValue(Object t, Field field) throws IllegalAccessException {
    Value annotationValue = field.getAnnotation(Value.class);
    if (annotationValue != null) {
      String propertyName = annotationValue.value();
      String propertyValue = map.get(propertyName);
      field.setAccessible(true);
      field.set(t, propertyValue);
    }

    RandomPrice annotationRandom = field.getAnnotation(RandomPrice.class);
    if (annotationRandom != null) {
      field.setAccessible(true);
      field.set(t, getRandomPrice());
    }
  }

  private String getRandomPrice() {
    DecimalFormat formatter = new DecimalFormat("#0.00"); // edited here.
    double min = 0;
    double max = 100000;
    double diff = max - min;
    double randomValue = 0L + Math.random() * diff;
    double tempRes = Math.floor(randomValue * 10);
    double finalRes = tempRes / 10;
    return formatter.format(finalRes);
  }
}
