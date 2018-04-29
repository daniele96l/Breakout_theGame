package com.mygdx.game.Player;

import com.badlogic.gdx.Input;
import help.Info;

public abstract class Player {
    protected String playerName;
    protected int lives;
    protected int score;

    public Player(String playerName) {
        this.playerName=playerName;
        this.lives= Info.defaultLivesNum;
        score=0;
    }
    public abstract int keyPressed();

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getLives() {
        return lives;
    }

    public int getScore() {
        return score;
    }
}
