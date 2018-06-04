package com.mygdx.game.Leaderboard;

/**
 *
 * La classe definisce il punteggio e il nome del giocatore da inserire nella classifica.
 * Implementa Comparable per poter essere confrontabile.
 *
 * @author Daniele Ligato
 */

public class Score implements Comparable {
    String name;
    int point;

    public Score(String name, int point) {
        this.name = name;
        this.point = point;

    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }

}

