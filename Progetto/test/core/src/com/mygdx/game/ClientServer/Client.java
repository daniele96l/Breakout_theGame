package com.mygdx.game.ClientServer;

import sprites.Paddle;

import javax.xml.crypto.Data;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class Client {
    private Socket socket;
    private DataInputStream is;
    private DataOutputStream os;
    private String host;
    private int port;
    private int key;
    String message;

    public Client() {
        port = 4444;
        key=0;
        try {
            socket = new Socket("localhost", port);
            is = new DataInputStream(socket.getInputStream());
            os = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ricevi() {
        String s;
        try {
            message="";

                s=is.readLine();
            //System.out.println(s);
                if(!s.equals(""))
                {
                    message += s+"\n";
                }

            os.flush();
            os.writeBytes(String.valueOf(key) + "\n");
            //System.out.println(message);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getMessage() {
        return message;
    }

    public void keyPressed(int key) {
        this.key = key;
    }
}

