package com.mygdx.game.ClientServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerThread extends Thread
{
    private Socket socket;
    private DataInputStream is;
    private DataOutputStream os;
    public ServerThread(Socket socket)
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
    public void run() {
        System.out.println("ciao");
    }
}
