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
    public void heal(Player player) {
        player.setCurrentState(Healthy.getInstance());
    }

    @Override
    public void injureEasily(Player player) {
        player.setCurrentState(Immobile.getInstance());
    }

    @Override
    public void injureHeavy(Player player) {
        player.setCurrentState(Immobile.getInstance());
    }
}
