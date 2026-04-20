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
    public void heal(Player player) {
        player.setCurrentState(Wounded.getInstance());
    }

    @Override
    public void injureEasily(Player player) {
        System.out.println("You are already immobile.");
    }

    @Override
    public void injureHeavy(Player player) {
        System.out.println("You are already immobile.");
    }
}
