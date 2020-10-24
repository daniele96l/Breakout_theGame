package com.mygdx.game.logic.Player;

import com.mygdx.game.help.Info;

/**
 * La classe che identifica il giocatore con le sue rispettive caratteristiche come numero di vite, nome, vite e punteggio.
 *
 * @author Regna, Ligato, Schillaci
 */

public abstract class Player {
    private String playerName;
    private int lives;
    private int score;

    Player(String playerName)
    {
        this.playerName=playerName;
        this.lives= Info.getInstance().getDefaultLivesNum();
        score=0;
    }
    public abstract int keyPressed();

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
