
package com.mygdx.game.clientserver;
import com.badlogic.gdx.Game;
import com.mygdx.game.logic.Player.CommandPlayer;
import com.mygdx.game.logic.GameManager.OnlineGameManager;
import javax.swing.*;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
/**
 *
 * Definisce l'oggetto Server che permette di instanziare una connessione tra diversi client, che si connettono tramite un Socket
 * alla porta definita.
 * La connessione tra client prevede uno scambio di pacchetti tramite UDP.
 *
 * @author Marco Mari, Marco Cotogni, Alessandro Oberti
 */

public class Server extends Game {
    private ArrayList<ServerThread> threadsIn;
    private int numeroPlayer = 1;
    private Icon icon = new ImageIcon("playersIcon.png");
    private String address;
    private ArrayList<String> names;
    private OnlineGameManager gameManager;
    /**
     *
     * Crea una nuova partita, istanziando gli N giocatori. A tale scopo viene richiesto all'utente che ha creato il server,
     * attraverso una finestra di dialogo, il numero di giocatori con cui intende giocare. Il numero deve essere compreso tra
     * 1 e 4, in caso contrario viene visualizzato un messaggio di errore.
     * Nel farlo, il metodo richiama un altro metodo della classe che permette di inizializzare il server.
     *
     */

    @Override
    public void create() {
        names = new ArrayList<String>();

        try {
            address = Inet4Address.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        String s = (String) JOptionPane.showInputDialog(null, "Enter the number of players\n Server IP: " + address, "Players", 1, icon, null, "");
        if (s != null && !s.isEmpty() && s.matches("[-+]?\\d*\\.?\\d+")) {
            numeroPlayer = Integer.parseInt(s);
            if (!(numeroPlayer > 0 && numeroPlayer < 5)) {
                JOptionPane.showMessageDialog(null, "Choose a number between 1-4", "Error", 1);
                System.exit(-1);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Input must be a number", "Error", 1);
            System.exit(-1);
        }
        initServer();
        gameManager = new OnlineGameManager(names, this);
    }

    /**
     * ridisegna lo scenario di gioco con una frequenza predefinita
     * @see com.mygdx.game.logic.GameManager.GameManager per il metodo "render()"
     */
    public void render() {
        gameManager.render();
    }

    /**
     * questo metodo serve per inizializzare il server inserendo il numero di porta e istanziando gli oggetti du cui fara uso.
     * Il metodo inoltre permette di riempire un array di stringhe contenente i nomi dei giocatori che si connettono.
     * Si occupa infine di fare iniziare il thread per i client connessi.
     *
     */

    private void initServer() {
        try {
            int portaServer = 4444;
            DatagramSocket datagramSocket = new DatagramSocket(portaServer);
            threadsIn = new ArrayList<ServerThread>();
            ArrayList<DatagramSocket> sockets = new ArrayList<DatagramSocket>();
            while (threadsIn.size() < numeroPlayer) {
                byte[] b = new byte[1024];
                DatagramPacket packet = new DatagramPacket(b, b.length);
                datagramSocket.receive(packet);
                String playerName = new String(packet.getData(), 0, packet.getLength());
                int newPort = portaServer + threadsIn.size() + 1;
                b = ((Integer) newPort).toString().getBytes();
                DatagramPacket packetBack = new DatagramPacket(b, b.length, packet.getAddress(), packet.getPort());
                datagramSocket.send(packetBack);
                sockets.add(new DatagramSocket(newPort));
                names.add(playerName);
                threadsIn.add(new ServerThread(sockets.get(sockets.size() - 1), packet.getAddress(), packet.getPort()));
            }

            datagramSocket.close();

            for (ServerThread t : threadsIn) {
                t.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * il metodo permette di dare il comando del movimento di un giocatore
     *
     * @param commandPlayer il giocatore che comanda il paddle
     * @param index l'indice del thread corrispondente al client
     *
     * @see CommandPlayer per il metodo "move(int key)"
     *
     */
    public void movePlayer(CommandPlayer commandPlayer, int index) {
        commandPlayer.move(threadsIn.get(index).getKey());
    }

    /**
     * Il metodo permette di inviare i messaggi ai client sotto forma di datagrammi, avendo stabilito una connessione UDP.
     * Il messaggio viene "scomposto", per così dire, in bytes.
     *
     * @param bytes è un array di byte che contine il messaggio da inviare ai client con un datagramma.
     *
     */
    public void sendMessage(byte[] bytes) {
        for (ServerThread thread : threadsIn) {
            DatagramPacket packet = new DatagramPacket(bytes, bytes.length, thread.getAddress(), thread.getPort());
            try {
                thread.getSocket().send(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < threadsIn.size(); i++) {
            if (threadsIn.get(i).isDeletable()) {
                threadsIn.remove(threadsIn.get(i));
                i--;
            }
        }
    }
}