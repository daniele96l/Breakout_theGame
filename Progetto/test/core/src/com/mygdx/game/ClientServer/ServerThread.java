package com.mygdx.game.ClientServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

public class ServerThread extends Thread
{
    private Socket socket;
    private DataInputStream is;
    private DataOutputStream os;
    private  int key=0;
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
        while (true) {
            try {
                key = is.readInt();
                System.out.println(key);
            } catch (EOFException e) {
                System.out.println("A");
                continue;
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public DataInputStream getIs() {
        return is;
    }

    public DataOutputStream getOs() {
        return os;
    }
}
