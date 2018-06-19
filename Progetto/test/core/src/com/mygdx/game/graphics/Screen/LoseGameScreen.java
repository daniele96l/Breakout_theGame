package com.mygdx.game.graphics.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.BreakGame;
import com.mygdx.game.graphics.Drawer;
import com.mygdx.game.logic.Resizer;

/**
 * @author Regna, Ligato, Schillaci
 *
 * Questa classe implementa la schermata che apparirà quando si perde
 *
 */
public class LoseGameScreen implements Screen {

    private BreakGame game;
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
        Texture gameOver = new Texture("gameover.jpg");
    }

    /**
     * Gestisce il proseguimento del programma, dopo che compare la schermata di "Loose"
     *
     * @param delta è l'intervallo di tempo che intercorre tra ogni chiamata del metodo render
     */
    @Override
    public void render(float delta)
    {
        Drawer.drawLoseScreen(game);
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            game.setScreen(new MainMenuScreen(game));
        }
    }

    /**
     * Ci permette di ridimensionare la finestra
     *
     * @param width larghezza della finestra
     * @param height altezza della finestra
     *
     */
    @Override
    public void resize(int width, int height) {
        int newHeight = height;
        int newWight = width;

        tempVet = resizer.toResize(height, width);
        float barreNere = tempVet[0];
        float coeffDimensionale = tempVet[1];

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
