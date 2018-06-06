
package com.mygdx.game.clientserver;
import com.badlogic.gdx.Game;
import com.mygdx.game.logic.Player.CommandPlayer;
import com.mygdx.game.logic.GameManager.OnlineGameManager;
import javax.swing.*;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;


/**
 * @Autor Marco Mari, Marco Cotogni, Alessandro Oberti
 *
 * Definisce l'oggetto Server che permette di instanziare una connessione tra diversi client, che si connettono tramite un Socket
 * alla porta definita.
 * La connessione tra client prevede uno scambio di pacchetti tramite UDP.
 *
 */

public class Server extends Game {
    private ArrayList<ServerThread> threadsIn;
    private int numeroPlayer = 1;
    private Icon icon = new ImageIcon("playersIcon.png");
    private String address;
    private ArrayList<String> names;
    private OnlineGameManager gameManager;


    /**
     * Crea una nuova partita, istanziando gli N giocatori
     *
     * @see:initServer()
     * @see:Info
     * @see:updateScene
     * @see:updateLevel
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


    public void render() {
        gameManager.render();
    }

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

    public void movePlayer(CommandPlayer commandPlayer, int index) {
        commandPlayer.move(threadsIn.get(index).getKey());
    }

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