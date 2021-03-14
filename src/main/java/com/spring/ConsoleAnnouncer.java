package com.spring;

public class ConsoleAnnouncer implements Announcer {

    @Inject
    private Reccomendator reccomendator;

    @Override
    public void announce(String message) {
        System.out.println(getClass() +  " " + message);
        reccomendator.recommend();
    }

}
