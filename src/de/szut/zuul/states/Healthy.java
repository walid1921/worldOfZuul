package de.szut.zuul.states;

import de.szut.zuul.Player;

public class Healthy implements State {

    private static Healthy instance;

    private Healthy() {}

    public static Healthy getInstance() {
        if (instance == null) {
            instance = new Healthy();
        }
        return instance;
    }

    @Override
    public State heal() {
        return this;
    }

    @Override
    public State injureEasily() {
        return Wounded.getInstance();
    }

    @Override
    public State injureHeavy() {
        return Immobile.getInstance();
    }
}
