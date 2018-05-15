package com.mygdx.game.ClientServer;

import sprites.Paddle;

import java.io.*;
import java.net.Socket;

public class ServerThreadIn extends Thread {
    private Socket socket;
    private DataInputStream is;
    private int key=0;
    private String line;

    public ServerThreadIn(Socket socket)
    {
        this.socket= socket;
        try
        {
            is= new DataInputStream(socket.getInputStream());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void run()
    {
        while (true) {
            try {

                System.out.println("IN");
                line = is.readLine();
                if (line != null) {
                    key = Integer.parseInt(line);
                    System.out.println(key);
                } else {
                    key = 0;
                }
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

}
