package com.mygdx.game.ClientServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;

public class ClientThread extends Thread {
    private DatagramSocket socket;
    private int port;
    private byte[] buf=new byte[1024];
    String message;

    public ClientThread(InetAddress address, int port,  DatagramSocket socket) {
        message = "";
        this.port = port;
        this.socket = socket;
        socket.connect(address, port);
    }

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

    public String getMessage() {
        return message;
    }


}
