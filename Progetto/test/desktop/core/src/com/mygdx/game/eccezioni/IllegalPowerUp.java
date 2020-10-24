package com.mygdx.game.eccezioni;

/**
 * Eccezione di tipologia di power up non valida
 *
 * @author Alberto Schillaci
 */

public class IllegalPowerUp extends Exception {
    public IllegalPowerUp() {
        super("Power up non valido");
    }
}
