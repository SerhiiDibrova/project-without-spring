package com.spring;

@Deprecated
public class PlayerImpl implements Player {

    @Inject
    private Reccomendator reccomendator;

    @PostConstruct
    public void init() {
        System.out.println(reccomendator.getClass());
    }

    @Override
    public void register() {
        System.out.println(getClass() + " registred!!");
    }
}
