package com.mygdx.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.BreakGame;
import com.mygdx.game.logic.Resizer;

/**
 * @author Ligato, Schillaci, Regna
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
     * @param game la  partita corrente
     *
     */
    public FinishScreen(BreakGame game) {
        this.game = game;
        resizer = new Resizer();
        tempVet = new float[2];
    }


    /**
     * il metodo assegna la nuova texture che dovrà essere disegnata quando vinci il livello, sulla quale sono presenti
     * le istruzioni per poter giungere al livello successivo
     */

    @Override
    public void show() {
        win = new Texture("nextlevel.jpg");
    }

    /**
     *
     * @param delta è l'itervallo di tempo che intercorre tra ogni chiamata del metodo render
     *
     * Renderizza il background e disegna la texture che ti avvisa di aver vinto il gioco
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
     *
     * @param width larghezza della finestra
     * @param height altezza della finestra
     *
     * Ci permetta di ridimensionare la finestra
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
