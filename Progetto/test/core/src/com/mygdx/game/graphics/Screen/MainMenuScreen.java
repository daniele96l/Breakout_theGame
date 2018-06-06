package com.mygdx.game.graphics.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.mygdx.game.BreakGame;
import com.mygdx.game.graphics.Drawer;
import com.mygdx.game.logic.InputTouch;
import com.mygdx.game.logic.Resizer;
import com.mygdx.game.logic.ScreenHandler;

/**
 * La classe che gestisce il memù principale con tutti i suoi bottoni
 *
 * @author Ligato, Schillaci, Regna
 */
public class MainMenuScreen implements Screen {

    private int newHeight, newWidth;
    BreakGame game;
    private float coeffDimensionale;
    private float barreNere = 0;
    private ScreenHandler screenHandler;

    private Resizer resizer;
    private float tempVet[];

    public MainMenuScreen(BreakGame game) {
        this.game = game;
        screenHandler = new ScreenHandler();
    }

    /**
     * Associa alle variabili le immagini che dovranno essere renderizate per essere visualizzate nella schermata
     */
    @Override
    public void show() {
        resizer = new Resizer();
        tempVet = new float[2];

    }


    /**
     * Disegna le parti grafiche che verranno visualizzate nel manù e si occupa di controllare se clicchi sopra alcune di queste, ovvero i bottoni
     *
     * @param delta è l'intervallo di tempo che intercorre tra ogni chiamata del metodo render
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Drawer.drawMainMenu(game);
        InputTouch.checkInputTouchMainMenu(newWidth,  game,  coeffDimensionale, screenHandler,  newHeight,  barreNere);
    }

    /**
     * si occupa di ridimensionare la finestra
     *
     * @param width larghezza della finestra
     * @param height altezza della finestra
     *
     */

    @Override
    public void resize(int width, int height) {
        this.newHeight = height;
        this.newWidth = width;

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
