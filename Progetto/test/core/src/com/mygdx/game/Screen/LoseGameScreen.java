package com.mygdx.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.BreakGame;
import com.mygdx.game.logic.Resizer;

/**
 * @author Regna, Ligato, Schillaci
 *
 * Questa classe implementa la schermata che apparirà quando si perde
 *
 */
public class LoseGameScreen implements Screen {

    private Texture gameOver;
    private BreakGame game;
    private int newHeight, newWight;
    private float coeffDimensionale, barreNere;
    private float tempVet[];
    private Resizer resizer;

    public LoseGameScreen(BreakGame game) {
        this.game = game;
        resizer = new Resizer();
        tempVet = new float[2];
    }

    /**
     * Indica il background da mostrare quando si perde
     */

    @Override
    public void show() {
        gameOver = new Texture("gameover.jpg");
    }

    /**
     * @param delta è l'intervallo di tempo che intercorre tra ogni chiamata del metodo render
     *
     * Gestisce il proseguimento del programma, dopo che compare la schermata di "Loose"
     */
    @Override
    public void render(float delta)
    {
        Drawer.drawLoseScreen(game,gameOver);

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
