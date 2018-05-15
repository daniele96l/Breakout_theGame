package com.mygdx.game.ClientServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

public class ServerThreadOut extends Thread {
    private Socket socket;
    private DataOutputStream os;
    private String message;

    public ServerThreadOut(Socket socket)
    {
        this.socket= socket;
        try
        {
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
            System.out.println("OUT");
            try {
                if(message==null) {
                    message="";
                }
                os.flush();
                os.writeBytes(message+"\n");
                System.out.println(message);

            } catch (EOFException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataOutputStream getOs() {
        return os;
    }

}
