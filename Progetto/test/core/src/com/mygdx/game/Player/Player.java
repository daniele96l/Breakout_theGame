package com.mygdx.game.Player;

import com.badlogic.gdx.Input;

public abstract class Player {
    protected String playerName;

    public Player(String playerName) {
        this.playerName=playerName;
    }
    public abstract int keyPressed();

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }
}
