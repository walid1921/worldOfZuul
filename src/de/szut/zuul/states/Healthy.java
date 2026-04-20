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
    public void heal(Player player) {
        // already healthy, nothing happens
        System.out.println("You are already healthy.");
    }

    @Override
    public void injureEasily(Player player) {
        player.setCurrentState(Wounded.getInstance());
    }

    @Override
    public void injureHeavy(Player player) {
        player.setCurrentState(Immobile.getInstance());
    }
}
