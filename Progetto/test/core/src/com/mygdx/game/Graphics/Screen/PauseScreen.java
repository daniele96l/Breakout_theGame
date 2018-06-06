/*
* DA FARE:
*
* mettere a posto la documentazione
*
*
*
* */


package com.mygdx.game.Graphics.Screen;

import com.badlogic.gdx.Screen;
import com.mygdx.game.BreakGame;
import com.mygdx.game.Graphics.Drawer;
import com.mygdx.game.logic.InputTouch;
import com.mygdx.game.logic.Resizer;
import com.mygdx.game.logic.ScreenHandler;

/**
 * Questa classe rappresenta la schermata di pausa con i relativi bottoni
 *
 * @author  Regna, Ligato, Schillaci
 */

public class PauseScreen implements Screen {

    private BreakGame game;
    private OfflineGameScreen oldScreen;
    private float newHeight, newWidth, coeffDimensionale = 1;
    private float barreNere =0;
    private ScreenHandler screenHandler;
    private Resizer resizer;
    private float tempVet[];

    /**
     * Prende i valori precedenti di game e old creen
     *
     * @param game
     * @param oldScreen ????
     *
     */
    public PauseScreen(BreakGame game, OfflineGameScreen oldScreen) {
        this.game = game;
        this.oldScreen=oldScreen;
        resizer = new Resizer();
        tempVet = new float[2];
    }

    /**
     * Assegna le texture ai valori delle nostre variabili
     *
     */
    @Override
    public void show() {
        screenHandler=new ScreenHandler();

    }

    /**
     * rendereizza a schermo tutte le texture necessarie e controlla dove clicca il cursore
     * MA IL CONTROLLO SE IL CURSORE CLICCA NON PUO ESSERE GESTITO DA QUALCUN ALTRO?????????????
     *
     * @param delta il tempo di aggiornamento dei frame
     */

    @Override
    public void render(float delta) { //Pattern Controller

        Drawer.drawPauseScreen(game);
        InputTouch.checkInputPauseScreen(newWidth, game, coeffDimensionale, screenHandler, newHeight, barreNere, oldScreen);

    }

    /**
     * Si occupa del resize della finestra
     *
     * @param width larghezza della finestra
     * @param height altezza della finestra
     */

    @Override
    public void resize(int width, int height) {

        this.newHeight = height;
        this.newWidth = width;
        barreNere = 0;

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
