/*
* DA FARE:
*
* mettere a posto la docum.
*
*
*
* */

package com.mygdx.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.BreakGame;
import com.mygdx.game.logic.Resizer;
import help.GameState;

/**
 * Si occupa della schermata di vittoria del gioco
 *
 * @author Regna, Scillaci, Ligato
 */

public class WinGameScreen implements Screen {

    private Texture youWin;
    private BreakGame game;
    private OfflineGameScreen oldScreen;
    private GameState gameState;
    private  int newHeight, newWight;
    private float barreNere, coeffDimensionale;
    private Resizer resizer;
    private float tempVet[];
    /**
     * Salva i parametri game, oldScreen, gameState
     *
     * @param game è la schermata di gioco
     * @param oldScreen ????
     * @param gameState Lo stato del gioco in quel momento
     */
    public WinGameScreen(BreakGame game, OfflineGameScreen oldScreen,GameState gameState) {
        this.game = game;
        this.oldScreen = oldScreen;
        this.gameState = gameState;
        resizer = new Resizer();
        tempVet = new float[2];
    }

    /**
     * Imposta la texture di sfondo
     */
    @Override
    public void show() {
        youWin = new Texture("nextlevel.jpg");
    }

    /**
     * Renderizza lo sfondo del gioco e si occupa di andare avanti se si preme space
     *
     * @param delta freq di aggiornamento frame
     */
    @Override
    public void render(float delta) {
        game.getBatch().begin();
        game.getBatch().draw(youWin, 0, 0);
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            gameState = GameState.ACTION;
            dispose();
            oldScreen.setGameState(GameState.ACTION);
            game.setScreen(oldScreen);
        }
        game.getBatch().end();
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
