package com.spring;

import lombok.SneakyThrows;

import java.lang.reflect.Field;

public class InjectAnnotationObjectConfigurator implements ObjectConfigurator {
    @Override
    @SneakyThrows
    public void configure(Object t, ApplicationContext context) {
        for (Field field : t.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Inject.class)) {
                Object object = context.getObject(field.getType());
                field.setAccessible(true);
                field.set(t, object);

            }
        }
    }
}
