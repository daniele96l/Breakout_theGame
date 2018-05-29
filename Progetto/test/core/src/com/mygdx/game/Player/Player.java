package com.mygdx.game.Player;

import com.badlogic.gdx.Input;
import help.Info;

/**
 * @author Regna, Ligato, Schillaci
 *
 * La classe che identifica il giocatore con le sue rispettive caratteristiche come, nome,vite, e punteggio.
 */

public abstract class Player {
    protected String playerName;
    protected int lives;
    protected int score;

    /**
     *
     * @param playerName è il nome del giocatore
     */

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
