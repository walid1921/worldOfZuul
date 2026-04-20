package de.szut.zuul.states;

import de.szut.zuul.Player;

public class Immobile implements State {

    private static Immobile instance;

    private Immobile() {}

    public static Immobile getInstance() {
        if (instance == null) {
            instance = new Immobile();
        }
        return instance;
    }

    @Override
    public State heal() {
        return Wounded.getInstance();
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
