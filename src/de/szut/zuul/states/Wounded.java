package de.szut.zuul.states;

import de.szut.zuul.Player;

public class Wounded implements State {

    private static Wounded instance;

    private Wounded() {}

    public static Wounded getInstance() {
        if (instance == null) {
            instance = new Wounded();
        }
        return instance;
    }

    @Override
    public State heal() {
        return Healthy.getInstance();
    }

    @Override
    public State injureEasily() {
        return Immobile.getInstance();
    }

    @Override
    public State injureHeavy() {
        return Immobile.getInstance();
    }
}
