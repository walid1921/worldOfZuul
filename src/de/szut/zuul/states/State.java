package de.szut.zuul.states;

import de.szut.zuul.Player;

public interface State {
    void heal(Player player);
    void injureEasily(Player player);
    void injureHeavy(Player player);
}
