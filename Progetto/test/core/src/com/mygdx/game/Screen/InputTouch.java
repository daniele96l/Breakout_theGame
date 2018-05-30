/*
* DA FARE:
*
* stesso discorso di Drawer: eliminare i parametri, fare information expert
*
* */


package com.mygdx.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.BreakGame;



public class InputTouch {
    public static void checkInputTouchMainMenu(int newWight, Texture scoreButtonText, BreakGame game, float coeffDimensionale, Texture playButtonText, ScreenHandler screenHandler, int newHeight, float barreNere, int scorebutton, int exitbutton, int playbutton, int onlinebutton, Texture exitButtonText, int offlinebutton) {
        if (Gdx.input.justTouched()) {
            if (Gdx.input.getX() > (newWight / 2) - (scoreButtonText.getWidth() * coeffDimensionale) / 2 && (Gdx.input.getX() < newWight / 2 + (scoreButtonText.getWidth() * coeffDimensionale) / 2) && (newHeight - Gdx.input.getY() > scorebutton * coeffDimensionale + barreNere) && (newHeight - Gdx.input.getY() < scorebutton * coeffDimensionale + scoreButtonText.getHeight() * coeffDimensionale + barreNere)) {
                screenHandler.gestisciMenu(game);
            }
            if (Gdx.input.getX() > (newWight / 2) - (playButtonText.getWidth() / 2 * coeffDimensionale) && (Gdx.input.getX() < newWight + (exitButtonText.getWidth() / 2) * coeffDimensionale) && (newHeight - Gdx.input.getY() > exitbutton * coeffDimensionale + barreNere && (newHeight - Gdx.input.getY() < exitbutton * coeffDimensionale + exitButtonText.getHeight() * coeffDimensionale + barreNere))) {
                screenHandler.exit();
            }
            if (Gdx.input.getX() > (newWight / 2) - (playButtonText.getWidth() / 2 * coeffDimensionale) && (Gdx.input.getX() < newWight + (playButtonText.getWidth() / 2) * coeffDimensionale) && (newHeight - Gdx.input.getY() > playbutton * coeffDimensionale + barreNere && (newHeight - Gdx.input.getY() < playbutton * coeffDimensionale + exitButtonText.getHeight() * coeffDimensionale + barreNere))) {
                screenHandler.singlePlayer(game);
            }
            if (Gdx.input.getX() > (newWight / 2) - (playButtonText.getWidth() / 2 * coeffDimensionale) && (Gdx.input.getX() < newWight + (playButtonText.getWidth() / 2) * coeffDimensionale) && (newHeight - Gdx.input.getY() > onlinebutton * coeffDimensionale + barreNere && (newHeight - Gdx.input.getY() < onlinebutton * coeffDimensionale + exitButtonText.getHeight() * coeffDimensionale + barreNere))) {

                screenHandler.multiplayerOffline(game);
            }
            if (Gdx.input.getX() > (newWight / 2) - (playButtonText.getWidth() / 2 * coeffDimensionale) && (Gdx.input.getX() < newWight + (playButtonText.getWidth() / 2) * coeffDimensionale) && (newHeight - Gdx.input.getY() > offlinebutton * coeffDimensionale + barreNere && (newHeight - Gdx.input.getY() < offlinebutton * coeffDimensionale + exitButtonText.getHeight() * coeffDimensionale + barreNere))) {
                screenHandler.multiplayerOnline(game);
            }
        }
    }

    public static void checkInputPauseScreen(float newWidth, BreakGame game, float coeffDimensionale, ScreenHandler screenHandler, float newHeight, float barreNere, Texture menuButton, OfflineGameScreen oldScreen) {
        if(Gdx.input.justTouched()) {
            if (Gdx.input.getX() > (newWidth / 2) - (menuButton.getWidth() / 2 * coeffDimensionale) && (Gdx.input.getX() < newWidth + (menuButton.getWidth() / 2) * coeffDimensionale) && (newHeight - Gdx.input.getY() > 150 * coeffDimensionale + barreNere && (newHeight - Gdx.input.getY() < 150 * coeffDimensionale + menuButton.getHeight() * coeffDimensionale + barreNere))) {
                screenHandler.pauseExit();
            }
            if (Gdx.input.getX() > (newWidth / 2) - (menuButton.getWidth() / 2 * coeffDimensionale) && (Gdx.input.getX() < newWidth + (menuButton.getWidth() / 2) * coeffDimensionale) && (newHeight - Gdx.input.getY() > 550 * coeffDimensionale + barreNere && (newHeight - Gdx.input.getY() < 550 * coeffDimensionale + menuButton.getHeight() * coeffDimensionale + barreNere))) {
                screenHandler.pauseResume(game, oldScreen);
            }
            if (Gdx.input.getX() > (newWidth / 2) - (menuButton.getWidth() / 2 * coeffDimensionale) && (Gdx.input.getX() < newWidth + (menuButton.getWidth() / 2) * coeffDimensionale) && (newHeight - Gdx.input.getY() > 350 * coeffDimensionale + barreNere && (newHeight - Gdx.input.getY() < 350 * coeffDimensionale + menuButton.getHeight() * coeffDimensionale + barreNere))) {
                screenHandler.mainMenu(game);
            }
        }
    }

    public static void checkInputScoreScreen(float newWidth, Texture backButton, float coeffDimensionale, BreakGame game, float newHeight, int backbuttony, float barreNere) {
        if (Gdx.input.getX() > (newWidth / 2) - (backButton.getWidth() / 2 * coeffDimensionale) && (Gdx.input.getX() < newWidth + (backButton.getWidth() / 2) * coeffDimensionale) && (newHeight - Gdx.input.getY() > backbuttony * coeffDimensionale + barreNere && (newHeight - Gdx.input.getY() < backbuttony * coeffDimensionale + backButton.getHeight() * coeffDimensionale + barreNere))) {
            if (Gdx.input.justTouched()) {
                game.setScreen(new MainMenuScreen(game));
            }
        }
    }
}
