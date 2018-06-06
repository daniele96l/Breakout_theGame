package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Graphics.Screen.MainMenuScreen;

/**
 * La classe rappresenta la schermata di gioco visualizzata sullo schermo
 *
 * @author ?
 */
public class BreakGame extends Game {
    private SpriteBatch batch;

    /**
     * il metodo crea la schermata di gioco.
     * Inizialmente viene visualizzato il MainMenuScreen.
     *
     */
    @Override
    public void create() {
        batch = new SpriteBatch();
        setScreen(new MainMenuScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    public SpriteBatch getBatch() {
        return batch;
    }
}

