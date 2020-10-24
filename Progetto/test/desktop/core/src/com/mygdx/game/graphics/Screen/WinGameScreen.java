/*
* DA FARE:
*
* mettere a posto la docum.
*
*
*
* */

package com.mygdx.game.graphics.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.BreakGame;
import com.mygdx.game.logic.Resizer;
import com.mygdx.game.help.GameState;

/**
 * Si occupa della schermata di vittoria del gioco
 *
 * @author Regna, Scillaci, Ligato
 */

public class WinGameScreen implements Screen {

    private Texture youWin;
    private BreakGame game;
    private OfflineGameScreen oldScreen;
    private Resizer resizer;
    private float tempVet[];

    public WinGameScreen(BreakGame game, OfflineGameScreen oldScreen,GameState gameState) {
        this.game = game;
        this.oldScreen = oldScreen;
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
        tempVet = resizer.toResize(height, width);
        float barreNere = tempVet[0];
        float coeffDimensionale = tempVet[1];
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {}
}
