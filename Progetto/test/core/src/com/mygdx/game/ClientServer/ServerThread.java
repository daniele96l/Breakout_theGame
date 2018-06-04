package com.mygdx.game.ClientServer;

import sprites.Paddle;

import java.io.*;
import java.net.*;

/**
 * @author  Cotogni, Mari, Oberti
 *
 * questa classe definisce il thread del server per ricevere i pacchetti dai diversi client
 */

public class ServerThread extends Thread {
    private DatagramSocket socket;
    private InetAddress address;
    private int port;
    private int key=0;
    private String line;
    private boolean deletable;

    public ServerThread(DatagramSocket socket, InetAddress address, int port) {

        this.socket = socket;
        this.address = address;
        this.port=port;
        deletable=false;
        socket.connect(address, port);
    }

    /**
     * Il metodo che viene chiamato quando viene fatto partire il thread
     */
    @Override
    public void run()
    {
        while (true) {
            try {
                byte[] bytes=new byte[1024];
                DatagramPacket packet=new DatagramPacket(bytes, bytes.length);
                socket.receive(packet);
                line=new String(packet.getData(), 0, packet.getLength());
                key=Integer.parseInt(line);
            } catch (EOFException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     *
     * @return un intero che rappresenta il tasto premuto dal giocatore
     */
    public int getKey() {
        return key;
    }

    /**
     *
     * @return ritorna la socket
     */


    public DatagramSocket getSocket() {
        return socket;
    }

    public InetAddress getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    public boolean isDeletable() {
        return deletable;
    }

    public void setDeletable(boolean deletable) {
        this.deletable = deletable;
    }
}
