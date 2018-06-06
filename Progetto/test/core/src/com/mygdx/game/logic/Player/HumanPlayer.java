package com.mygdx.game.logic.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

/**
 *
 * la classe definisce un giocatore che controlla il proprio paddle usando i tasti della keyboard
 *
 * @author Schillaci
 */

public class HumanPlayer extends Player {

    /**
     *
     * @param playerName è il nome del giocatore
     */

    public HumanPlayer(String playerName) {
        super(playerName);
    }

    /**
     * il metodo ritorna un valore intero che corrisponde al tasto LEFT, RIGHT oppure al tasto P, a seconda
     * di quale di questi viene premuto.
     *
     * @see Input.Keys
     * @return ritorna il numero corrispondente al tasto premuto dal giocatore
     */

    @Override
    public int keyPressed() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && !Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            return Input.Keys.LEFT;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && !Gdx.input.isKeyPressed(Input.Keys.LEFT))
            return Input.Keys.RIGHT;
        if (Gdx.input.isKeyPressed(Input.Keys.P))
            return Input.Keys.P;

        return Input.Keys.ANY_KEY;
    }
}
