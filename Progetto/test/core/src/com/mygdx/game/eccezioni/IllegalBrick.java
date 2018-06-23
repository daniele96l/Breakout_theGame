package com.mygdx.game.eccezioni;

/**
 * Eccezione di tipologia di mattoncino non valida
 *
 * @author Alberto Schillaci
 */
public class IllegalBrick extends Exception{
    public IllegalBrick() {
        super("Brick non valido");
    }

}
