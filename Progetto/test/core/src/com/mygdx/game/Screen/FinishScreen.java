package com.mygdx.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Scaling;
import com.mygdx.game.BreakGame;
import com.sun.org.apache.regexp.internal.RE;

/**
 * @author ligato,schillaci, regna
 *
 * Gestisce la schermata di fine gioco
 */

public class FinishScreen implements Screen {

    BreakGame game;
    private Texture win;
    private int newHeight, newWight;
    private float tempVet[];
    private Resizer resizer;
    private float coeffDimensionale, barreNere;

    /**
     *
     * @param game oggetto Breack game
     */
    public FinishScreen(BreakGame game) {
        this.game = game;
        resizer = new Resizer();
        tempVet = new float[2];
    }


    /**
     * quando vinci il gioco asegna la nuova texture che dovrà essere disegnata
     */

    @Override
    public void show() {
        win = new Texture("nextlevel.jpg");

    }

    /**
     * Renderizza il back e disegna la texture che ti avvisa di aver vinto il gioco
     * @param delta
     */
    @Override
    public void render(float delta) {
        game.getBatch().begin();

        game.getBatch().draw(win, 0, 0);

        if(Gdx.input.isKeyPressed(Input.Keys.ENTER))
            dispose();
            game.setScreen(new MainMenuScreen(game));

        game.getBatch().end();
    }

    /**
     * Ci permetta di ridimensionare la finestra
     *
     * @param width larghezza della finestra
     * @param height altezza della finestra
     */
    @Override
    public void resize(int width, int height) {

        this.newHeight = height;
        this.newWight = width;

        tempVet = resizer.toResize(height, width);
        barreNere = tempVet[0];
        coeffDimensionale = tempVet[1];

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
