package com.mygdx.game.ClientServer;

import sprites.Paddle;

import java.io.*;
import java.net.Socket;

public class ServerThreadIn extends Thread
{
    private Socket socket;
    private DataInputStream is;
    private DataOutputStream os;
    private int key=0;
    private String message;
    String line;

    public ServerThreadIn(Socket socket)
    {
        this.socket= socket;
        try
        {
            is= new DataInputStream(socket.getInputStream());
            os= new DataOutputStream(socket.getOutputStream());
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
                if(message==null) {
                    message="";
                }
                os.flush();
                os.writeBytes(message+"\n");

                line = is.readLine();
                if (line != null) {
                    key = Integer.parseInt(line);
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

    public void setMessage(String message) {
        this.message = message;
    }

    public DataInputStream getIs() {
        return is;
    }

    public DataOutputStream getOs() {
        return os;
    }

}
