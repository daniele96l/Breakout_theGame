/*
* DA FARE:
*
* stesso discorso di Drawer: eliminare i parametri, fare information expert
*
* */


package com.mygdx.game.logic;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.BreakGame;
import com.mygdx.game.graphics.sprites.Button.ButtonCollection;
import com.mygdx.game.graphics.Screen.MainMenuScreen;
import com.mygdx.game.graphics.Screen.OfflineGameScreen;
import com.mygdx.game.graphics.sprites.Button.Button;

import java.util.ArrayList;

/**
 *
 * @author regna
 *
 * questa classe si occupa di controllare quali e quando sono stati toccati i bottoni
 *
 */


public class InputTouch {

    /**
     * Questo metodo cotrolla i click sui bottoni del Menu Principale
     * @param newWight
     * @param game
     * @param coeffDimensionale
     * @param screenHandler
     * @param newHeight
     * @param barreNere
     */

    public static void checkInputTouchMainMenu(int newWight, BreakGame game, float coeffDimensionale, ScreenHandler screenHandler, int newHeight, float barreNere) {
        ArrayList<Button> menuButtons=ButtonCollection.getInstance().getMenuButtons();

        if (Gdx.input.justTouched()) {
            if (Gdx.input.getX() > (newWight / 2) - ( menuButtons.get(4).getWidth() * coeffDimensionale) / 2 && (Gdx.input.getX() < newWight / 2 + (menuButtons.get(4).getWidth() * coeffDimensionale) / 2) && (newHeight - Gdx.input.getY() > menuButtons.get(4).getY() * coeffDimensionale + barreNere) && (newHeight - Gdx.input.getY() < menuButtons.get(4).getY() * coeffDimensionale + menuButtons.get(4).getHeight() * coeffDimensionale + barreNere)) {
                screenHandler.gestisciMenu(game);
            }
            if (Gdx.input.getX() > (newWight / 2) - (menuButtons.get(1).getWidth() / 2 * coeffDimensionale) && (Gdx.input.getX() < newWight + (menuButtons.get(1).getWidth() / 2) * coeffDimensionale) && (newHeight - Gdx.input.getY() > menuButtons.get(1).getY() * coeffDimensionale + barreNere && (newHeight - Gdx.input.getY() < menuButtons.get(1).getY() * coeffDimensionale + menuButtons.get(1).getHeight() * coeffDimensionale + barreNere))) {
                screenHandler.exit();
            }
            if (Gdx.input.getX() > (newWight / 2) - (menuButtons.get(0).getWidth() / 2 * coeffDimensionale) && (Gdx.input.getX() < newWight + (menuButtons.get(0).getWidth() / 2) * coeffDimensionale) && (newHeight - Gdx.input.getY() > menuButtons.get(0).getY() * coeffDimensionale + barreNere && (newHeight - Gdx.input.getY() < menuButtons.get(0).getY() * coeffDimensionale + menuButtons.get(0).getHeight() * coeffDimensionale + barreNere))) {
                screenHandler.singlePlayer(game);
            }
            if (Gdx.input.getX() > (newWight / 2) - (menuButtons.get(2).getWidth() / 2 * coeffDimensionale) && (Gdx.input.getX() < newWight + (menuButtons.get(2).getWidth() / 2) * coeffDimensionale) && (newHeight - Gdx.input.getY() > menuButtons.get(2).getY() * coeffDimensionale + barreNere && (newHeight - Gdx.input.getY() < menuButtons.get(2).getY() * coeffDimensionale + menuButtons.get(2).getHeight() * coeffDimensionale + barreNere))) {

                screenHandler.multiplayerOffline(game);
            }
            if (Gdx.input.getX() > (newWight / 2) - (menuButtons.get(3).getWidth() / 2 * coeffDimensionale) && (Gdx.input.getX() < newWight + (menuButtons.get(3).getWidth() / 2) * coeffDimensionale) && (newHeight - Gdx.input.getY() > menuButtons.get(3).getY() * coeffDimensionale + barreNere && (newHeight - Gdx.input.getY() < menuButtons.get(3).getY() * coeffDimensionale + menuButtons.get(3).getHeight() * coeffDimensionale + barreNere))) {
                screenHandler.multiplayerOnline(game);
            }
        }
    }

    /**
     * Questo metodo controlla i click sui bottoni del menu di pausa
     * @param newWidth
     * @param game
     * @param coeffDimensionale
     * @param screenHandler
     * @param newHeight
     * @param barreNere
     * @param oldScreen
     */

    public static void checkInputPauseScreen(float newWidth, BreakGame game, float coeffDimensionale, ScreenHandler screenHandler, float newHeight, float barreNere, OfflineGameScreen oldScreen) {
        ArrayList<Button> pauseButtons=ButtonCollection.getInstance().getPauseButtons();

        if(Gdx.input.justTouched()) {
            if (Gdx.input.getX() > (newWidth / 2) - (pauseButtons.get(1).getWidth() / 2 * coeffDimensionale) && (Gdx.input.getX() < newWidth + (pauseButtons.get(1).getWidth() / 2) * coeffDimensionale) && (newHeight - Gdx.input.getY() > pauseButtons.get(1).getY() * coeffDimensionale + barreNere && (newHeight - Gdx.input.getY() < pauseButtons.get(1).getY() * coeffDimensionale + pauseButtons.get(1).getHeight() * coeffDimensionale + barreNere))) {
                screenHandler.pauseExit();
            }
            if (Gdx.input.getX() > (newWidth / 2) - (pauseButtons.get(0).getWidth() / 2 * coeffDimensionale) && (Gdx.input.getX() < newWidth + (pauseButtons.get(0).getWidth() / 2) * coeffDimensionale) && (newHeight - Gdx.input.getY() > pauseButtons.get(0).getY() * coeffDimensionale + barreNere && (newHeight - Gdx.input.getY() < pauseButtons.get(0).getY() * coeffDimensionale + pauseButtons.get(0).getHeight() * coeffDimensionale + barreNere))) {
                screenHandler.pauseResume(game, oldScreen);
            }
            if (Gdx.input.getX() > (newWidth / 2) - (pauseButtons.get(2).getWidth() / 2 * coeffDimensionale) && (Gdx.input.getX() < newWidth + (pauseButtons.get(2).getWidth() / 2) * coeffDimensionale) && (newHeight - Gdx.input.getY() > pauseButtons.get(2).getY() * coeffDimensionale + barreNere && (newHeight - Gdx.input.getY() < pauseButtons.get(2).getY() * coeffDimensionale + pauseButtons.get(2).getHeight() * coeffDimensionale + barreNere))) {
                screenHandler.mainMenu(game);
            }
        }
    }

    /**
     * questo metodo controlla i click sul bottone del menù dei punteggi
     * @param newWidth
     * @param coeffDimensionale
     * @param game
     * @param newHeight
     * @param barreNere
     */

    public static void checkInputScoreScreen(float newWidth, float coeffDimensionale, BreakGame game, float newHeight, float barreNere) {
        ArrayList<Button> scoreButtons=ButtonCollection.getInstance().getScoreButtons();

        if (Gdx.input.getX() > (newWidth / 2) - (scoreButtons.get(0).getWidth() / 2 * coeffDimensionale) && (Gdx.input.getX() < newWidth + (scoreButtons.get(0).getWidth() / 2) * coeffDimensionale) && (newHeight - Gdx.input.getY() > scoreButtons.get(0).getY() * coeffDimensionale + barreNere && (newHeight - Gdx.input.getY() < scoreButtons.get(0).getY() * coeffDimensionale + scoreButtons.get(0).getHeight() * coeffDimensionale + barreNere))) {
            if (Gdx.input.justTouched()) {
                game.setScreen(new MainMenuScreen(game));
            }
        }
    }
}
