package com.mygdx.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.mygdx.game.BreakGame;

import javax.swing.*;

import static com.sun.deploy.uitoolkit.ToolkitStore.dispose;

public class ScreenHandler
{
    public ScreenHandler() {

    }

    void gestisciMenu(BreakGame game) {
        game.setScreen(new ScoreScreen(game));
    }

    void exit() {
        Gdx.app.exit();
    }

    void singlePlayer(BreakGame game)
    {
        OfflineGameScreen.setPlayerName(JOptionPane.showInputDialog(null, "Enter a nickname", "Nickname ", 1));
        if (OfflineGameScreen.getPlayerName() != null && !OfflineGameScreen.getPlayerName().isEmpty()) {
            game.setScreen(new OfflineGameScreen(game, 1));
        }
    }
    void multiplayerOffline(BreakGame game)
    {
        int numeroPlayer;
        OfflineGameScreen.setPlayerName(JOptionPane.showInputDialog(null, "Enter a nickname", "Nickname ", 1));
        numeroPlayer = (Integer.parseInt(JOptionPane.showInputDialog(null, "Number of player", "Enter the number of player ", 1)));
        game.setScreen(new OfflineGameScreen(game, numeroPlayer));
    }
    void pauseExit(){
        try
        {
            dispose();
            Gdx.app.exit();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    void mainMenu(BreakGame game)
    {
        try {
            dispose();
            game.setScreen(new MainMenuScreen(game));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    void pauseResume(BreakGame game, OfflineGameScreen oldScreen)
    {
        try
        {
            dispose();
            game.setScreen(oldScreen);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    void multiplayerOnline(BreakGame game)
    {
        game.setScreen(new MultiplayerGameScreen(game));
    }
}

