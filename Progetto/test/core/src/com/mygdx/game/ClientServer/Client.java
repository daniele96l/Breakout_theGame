package com.mygdx.game.ClientServer;

import javax.xml.crypto.Data;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client
{
    private Socket socket;
    private DataInputStream is;
    private DataOutputStream os;
    private String host;
    private int port;
    private int key;
    public Client()
    {
        port=4444;
        try
        {
            socket= new Socket("127.0.0.1",port);
            is= new DataInputStream(socket.getInputStream());
            os= new DataOutputStream(socket.getOutputStream());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    public void messaggio(String i)
    {
        String s=i.concat("\n");
        try
        {
            os.flush();
            os.writeBytes(s);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    public void ricevi()
    {
        String s;
        try
        {
            s=is.readLine();
            System.out.println(s);
        }
        catch(IOException e)
        {
            e.getMessage();
        }
    }

    public void keyPressed(int key) {
        this.key=key;
        try {
            os.writeInt(this.key);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public int getKey() {
        return key;
    }
}
