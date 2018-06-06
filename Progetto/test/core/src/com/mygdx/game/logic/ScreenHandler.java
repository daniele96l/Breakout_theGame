package com.mygdx.game.logic;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.BreakGame;
import com.mygdx.game.Graphics.Screen.MainMenuScreen;
import com.mygdx.game.Graphics.Screen.MultiplayerGameScreen;
import com.mygdx.game.Graphics.Screen.OfflineGameScreen;
import com.mygdx.game.Graphics.Screen.ScoreScreen;

import javax.swing.*;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author ?
 *
 * La classe gestisce alcuni eventi delle schermate permettendo la visualizzazione di alcune finestre di
 * dialogo per l'iserimento di informazioni varie
 */

public class ScreenHandler
{
    private ImageIcon ipIcon = new ImageIcon("ipIcon.png");
    private ImageIcon nickIcon = new ImageIcon("nickIcon.png");

    public ScreenHandler() {

    }

    /**
     *
     * @param game è la schermata di gioco
     *
     */
    void gestisciMenu(BreakGame game) {
        game.setScreen(new ScoreScreen(game));
    }

    void exit() {
        Gdx.app.exit();
    }

    /**
     * @param game è la schermata di gioco
     *
     * il metodo si occupa di far comparire una finestra di dialogo con l'utente che ha selezionato la modalità
     * di gioco "Single Player" in cui viene chiesto il nickName con cui vuole giocare.
     * Una volta che l'utente ha inserito il nickname, viene creata la schermata di gioco offline.
     * Se l'utente non inserisce il nome, compare una schermata di errore.
     *
     */
    void singlePlayer(BreakGame game)
    {
        OfflineGameScreen.setPlayerName(JOptionPane.showInputDialog(null, "Enter a nickname", "Nickname ", 1));
        if (OfflineGameScreen.getPlayerName() != null && !OfflineGameScreen.getPlayerName().isEmpty()) {
            game.setScreen(new OfflineGameScreen(game, 1));
        } else {
            JOptionPane.showMessageDialog(null, "Insert a valid nickname", "Error nickname ", 1);
        }
    }

    /**
     * @param game è la schermata di gioco
     *
     * Il metodo si occupa di far comparire una finestra di dialogo con l'utente che ha selezionato la modalità
     * di gioco "Multiplayer Offline" in cui viene richiesto il nickname con cui l'utente vuole giocare. Se l'utente
     * non inserisce il nome, compare una finestra di errore. Viene poi  fatta comparire una finestra di dialogo
     * in cui viene richiesto il numero di giocatori con cui con cui l'utente intende giocare. Ancora una volta viene
     * mostrata una finestra di errore se l'utente non inserisce nulla o se inserisce un numero errato o qualcosa di
     * diverso da un numero.
     *
     */
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

    /**
     * il metodo si occupa della chiusura della schermata quando viene premuto il tasto "EXIT" nella schermata di pausa.
     */
    void pauseExit(){
        try
        {
            Gdx.app.exit();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * @param game è la schermata di gioco
     *
     * il metodo si occupa di far visualizzare il menù principale sulla schermata di gioco
     *
     */
    void mainMenu(BreakGame game)
    {
        try {
            game.setScreen(new MainMenuScreen(game));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * ??????
     * @param game
     * @param oldScreen
     */
    void pauseResume(BreakGame game, OfflineGameScreen oldScreen)
    {
        try
        {
            game.setScreen(oldScreen);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * il metodo si occupa di far comparire una finestra di dialogo con l'utente che ha selezionato la modalità di
     * gioco "Multiplayer Online" in cui viene richesto l'indirizzo IP del server a cui ci si deve connettere per
     * giocare. Qualora la connessione vada a buon fine, viene richiesto all'utente il nickname con cui intende giocare.
     * Per entrambe le finestre di dialogo, viene visualizzato un messaggio di errore se l'utente non inserisce
     * correttamente le informazioni richieste.
     *
     * @param game è la schermata di gioco
     */
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

    /**
     * descrizione: ...??
     *
     * @param s
     * @return
     */
    private boolean isNumeric(String s) {
        return s != null && s.matches("[-+]?\\d*\\.?\\d+");
    }
}

