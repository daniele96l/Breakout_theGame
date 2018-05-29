package com.mygdx.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.BreakGame;
import com.mygdx.game.logic.Resizer;
import help.GameState;
import help.Info;

/**
 * @Author  Regna, Ligato, Schillaci
 *
 * Questa classe rappresenta la schermata di pausa con i relativi bottoni
 *
 */

public class PauseScreen implements Screen {

    private BreakGame game;
    private GameState gameState;
    private Texture menu;
    private Texture resumeButton;
    private Texture exitButton;
    private Texture menuButton;
    private OfflineGameScreen oldScreen;
    private float newHeight, newWight, coeffDimensionale = 1;
    private float barreNere =0;
    private ScreenHandler screenHandler;
    private Resizer resizer;
    private float tempVet[];

    /**
     *
     * @param game ????
     * @param oldScreen ????
     *
     * Prende i valori precedenti di game e old creen
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
        menu = new Texture("menuscreen.jpg");
        menuButton = new Texture("menu.png");
        resumeButton = new Texture("resume.png");
        exitButton = new Texture("exit.png");
        resumeButton = new Texture("resume.png");
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

        game.getBatch().begin();
        game.getBatch().draw(menu, 0, 0);
        game.getBatch().draw(resumeButton, Info.larghezza / 2 - resumeButton.getWidth() / 2, 550);//alpostodimetterlicosipossousaredellecostanti
        game.getBatch().draw(exitButton, Info.larghezza / 2 - exitButton.getWidth() / 2, 150);
        game.getBatch().draw(menuButton, Info.larghezza / 2 - menuButton.getWidth() / 2, 350);

        if(Gdx.input.justTouched()) {
            if (Gdx.input.getX() > (newWight / 2) - (menuButton.getWidth() / 2 * coeffDimensionale) && (Gdx.input.getX() < newWight + (menuButton.getWidth() / 2) * coeffDimensionale) && (newHeight - Gdx.input.getY() > 150 * coeffDimensionale + barreNere && (newHeight - Gdx.input.getY() < 150 * coeffDimensionale + menuButton.getHeight() * coeffDimensionale + barreNere))) {
                screenHandler.pauseExit();
            }
            if (Gdx.input.getX() > (newWight / 2) - (menuButton.getWidth() / 2 * coeffDimensionale) && (Gdx.input.getX() < newWight + (menuButton.getWidth() / 2) * coeffDimensionale) && (newHeight - Gdx.input.getY() > 550 * coeffDimensionale + barreNere && (newHeight - Gdx.input.getY() < 550 * coeffDimensionale + menuButton.getHeight() * coeffDimensionale + barreNere))) {
                screenHandler.pauseResume(game,oldScreen);
            }
            if (Gdx.input.getX() > (newWight / 2) - (menuButton.getWidth() / 2 * coeffDimensionale) && (Gdx.input.getX() < newWight + (menuButton.getWidth() / 2) * coeffDimensionale) && (newHeight - Gdx.input.getY() > 350 * coeffDimensionale + barreNere && (newHeight - Gdx.input.getY() < 350 * coeffDimensionale + menuButton.getHeight() * coeffDimensionale + barreNere))) {
                screenHandler.mainMenu(game);
            }
        }
                game.getBatch().end();

    }

    /**
     * Si occupa del resize della finestra
     *
     * @param width larghezza della finestra
     *
     * @param height altezza della finestra
     */

    @Override
    public void resize(int width, int height) {

        this.newHeight = height;
        this.newWight = width;
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
