package com.mygdx.game.ClientServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server
{
    private ServerSocket serverSocket;
    private Socket socket1;
    private Socket socket2;
    private DataInputStream is;
    private DataOutputStream os;
    private int porta;
    private ArrayList<Thread> threads;

    public Server()
    {
        this.porta=4444;
        try
        {
            serverSocket= new ServerSocket(porta);
            threads= new ArrayList<Thread>();
            while(threads.size()<2)
            {
                if (threads.size() == 0) {
                    socket1 = serverSocket.accept();
                    is = new DataInputStream(socket1.getInputStream());
                    threads.add(new ServerThread(socket1));
                    //System.out.println("Aggiunto: " + threads.get(threads.size() - 1).getName());
                }
                else {
                    socket2 = serverSocket.accept();
                    threads.add(new ServerThread(socket2));
                    os = new DataOutputStream(socket2.getOutputStream());
                }
                //threads.get(threads.size()-1).start();
                System.out.println(threads.size());
            }

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    public void gestisciMessaggi()
    {
        String s1;
        int flag= 0;
            try
            {
                while(flag==0)
                {
                    s1 = is.readLine();
                    if (s1 != null) {
                        System.out.println(s1);
                        os.writeBytes(s1+"\n");
                        os.flush();
                        flag = 1;
                    }
                    else {
                        System.out.println("porcoiddio!");
                        flag = 1;
                    }
                }


            }
            catch (IOException e)
            {
                e.printStackTrace();
            }


    }

}
