/*
* DA FARE:
*
* stesso discorso di Drawer: eliminare i parametri, fare information expert
*
* */


package com.mygdx.game.logic;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.BreakGame;
import com.mygdx.game.Graphics.Screen.ButtonCollection;
import com.mygdx.game.Graphics.Screen.MainMenuScreen;
import com.mygdx.game.Graphics.Screen.OfflineGameScreen;
import com.mygdx.game.Graphics.sprites.Button;

import java.util.ArrayList;

/**
 *
 * @author ?
 *SCRIVERE INDICE DEI BOTTONI DEGLI ARRAYLIST
 * ? ? ? ?
 */


public class InputTouch {

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

    public static void checkInputScoreScreen(float newWidth, float coeffDimensionale, BreakGame game, float newHeight, float barreNere) {
        ArrayList<Button> scoreButtons=ButtonCollection.getInstance().getScoreButtons();

        if (Gdx.input.getX() > (newWidth / 2) - (scoreButtons.get(0).getWidth() / 2 * coeffDimensionale) && (Gdx.input.getX() < newWidth + (scoreButtons.get(0).getWidth() / 2) * coeffDimensionale) && (newHeight - Gdx.input.getY() > scoreButtons.get(0).getY() * coeffDimensionale + barreNere && (newHeight - Gdx.input.getY() < scoreButtons.get(0).getY() * coeffDimensionale + scoreButtons.get(0).getHeight() * coeffDimensionale + barreNere))) {
            if (Gdx.input.justTouched()) {
                game.setScreen(new MainMenuScreen(game));
            }
        }
    }
}
