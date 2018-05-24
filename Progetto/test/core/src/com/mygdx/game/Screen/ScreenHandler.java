package com.mygdx.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.mygdx.game.BreakGame;

import javax.swing.*;

import java.net.InetAddress;
import java.net.UnknownHostException;


public class ScreenHandler
{
    private ImageIcon ipIcon = new ImageIcon("ipIcon.png");
    private ImageIcon nickIcon = new ImageIcon("nickIcon.png");

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
        } else {
            JOptionPane.showMessageDialog(null, "Insert a valid nickname", "Error nickname ", 1);
        }
    }

    void multiplayerOffline(BreakGame game)
    {
        String numeroPlayer;
        OfflineGameScreen.setPlayerName(JOptionPane.showInputDialog(null, "Enter a nickname", "Nickname ", 1));
        if (OfflineGameScreen.getPlayerName() == null || OfflineGameScreen.getPlayerName().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Insert a valid nickname", "Error nickname ", 1);
        }
        else {
            numeroPlayer = ((JOptionPane.showInputDialog(null, "Number of player", "Enter the number of player ", 1)));
            if (!isNumeric(numeroPlayer)) {
                JOptionPane.showMessageDialog(null, "Insert a valid number of player (1-4)", "Error number of player ", 1);
            }
            else {
                if(Integer.parseInt(numeroPlayer) > 0 && Integer.parseInt(numeroPlayer) <= 4) {
                    game.setScreen(new OfflineGameScreen(game, Integer.parseInt(numeroPlayer)));
                }
                else {
                    JOptionPane.showMessageDialog(null, "Insert a valid number of player (1-4)", "Error number of player ", 1);

                }
            }
        }
    }
    void pauseExit(){
        try
        {
            Gdx.app.exit();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    void mainMenu(BreakGame game)
    {
        try {
            game.setScreen(new MainMenuScreen(game));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    void pauseResume(BreakGame game, OfflineGameScreen oldScreen)
    {
        try
        {
            game.setScreen(oldScreen);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    void multiplayerOnline(BreakGame game)
    {

        InetAddress address = null;
        try {
            address = InetAddress.getByName((String) JOptionPane.showInputDialog(null, "Enter the IP address", "Server IP", 1, ipIcon, null, ""));
            String playerName = (String) JOptionPane.showInputDialog(null, "Insert your Nickname", "Nickname", 1, nickIcon, null, "");
            if(playerName != null && !playerName.isEmpty()) {
                game.setScreen(new MultiplayerGameScreen(game, address, playerName));
            }
            else {
                JOptionPane.showMessageDialog(null, "Invalid nickname", "Nickname error", 1);

            }
        } catch (UnknownHostException e) {
            JOptionPane.showMessageDialog(null, "Invalid address", "Address", 1);

        }
    }


    private boolean isNumeric(String s) {
        return s != null && s.matches("[-+]?\\d*\\.?\\d+");
    }
}

