package com.mygdx.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.BreakGame;


public class InputTouch {

    public static void checkInputTouch(int newWight, Texture scoreButtonText, BreakGame game, float coeffDimensionale, Texture playButtonText, ScreenHandler screenHandler, int newHeight, float barreNere, int scorebutton, int exitbutton, int playbutton, int onlinebutton, Texture exitButtonText, int offlinebutton) {
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
}
