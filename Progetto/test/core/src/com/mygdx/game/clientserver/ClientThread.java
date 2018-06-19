package com.mygdx.game.clientserver;

import java.io.IOException;
import java.net.*;

/**
 * Definisce il comportamento del client nella connesione con il server
 *
 * @author Marco Mari, Marco Cotogni, Alessandro Oberti
 *
 */
public class ClientThread extends Thread {
    private DatagramSocket socket;
    private byte[] buf=new byte[4096];
    private String message;

    /**
     *
     * @param address indirizzo IP
     * @param port porta per la connesione
     * @param socket  La socket
     *
     *
     */

    public ClientThread(InetAddress address, int port,  DatagramSocket socket) {
        message = "";
        int port1 = port;
        this.socket = socket;
        socket.connect(address, port);
    }

    /**
     * Metodo che viene chiamato ogni volta che parte un Thread
     */
    public void run() {
        while (true) {
            try {
                DatagramPacket packet=new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                message=new String(packet.getData(),0,packet.getLength());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Ritorna il messaggio
     * @return Stringa
     */
    public String getMessage() {
        return message;
    }


}
