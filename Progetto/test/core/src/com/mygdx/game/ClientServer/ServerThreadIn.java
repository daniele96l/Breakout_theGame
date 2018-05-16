package com.mygdx.game.ClientServer;

import sprites.Paddle;

import java.io.*;
import java.net.*;

public class ServerThreadIn extends Thread {
    private DatagramSocket socket;
    private InetAddress address;
    private int port;
    private int key=0;
    private String line;

    public ServerThreadIn(DatagramSocket socket,InetAddress address, int port) {

        this.socket = socket;
        this.address = address;
        this.port=port;
        socket.connect(address, port);
    }

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

    public int getKey() {
        return key;
    }

    public DatagramSocket getSocket() {
        return socket;
    }

    public InetAddress getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }
}
