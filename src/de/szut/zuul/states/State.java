package de.szut.zuul.states;

import de.szut.zuul.Player;

public interface State {
    State heal();
    State injureEasily();
    State injureHeavy();
}
