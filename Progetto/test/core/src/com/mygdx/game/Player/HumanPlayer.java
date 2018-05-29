package com.mygdx.game.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

/**
 * @author Schillaci
 *
 * la classe definisce un giocatore che controlla il proprio paddle usando i tasti della keyboard
 *
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
     * @return ritorna il numero corrispondente al tasto premuto dal giocatore
     *
     * @see Input.Keys
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
