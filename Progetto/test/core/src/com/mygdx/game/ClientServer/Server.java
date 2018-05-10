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
    private ArrayList<Socket> sockets;
    private int porta;
    private ArrayList<DataInputStream> inputStreams;
    private ArrayList<DataOutputStream> outputStreams;
    private ArrayList<ServerThread> threads;

    public Server()
    {
        this.porta=4444;
        sockets=new ArrayList<Socket>();
        inputStreams=new ArrayList<DataInputStream>();
        outputStreams=new ArrayList<DataOutputStream>();
        try
        {
            serverSocket= new ServerSocket(porta);
            threads= new ArrayList<ServerThread>();
            while(threads.size()<1) {
                sockets.add(serverSocket.accept());
                inputStreams.add(new DataInputStream(sockets.get(sockets.size() - 1).getInputStream()));
                outputStreams.add(new DataOutputStream(sockets.get(sockets.size() - 1).getOutputStream()));
                threads.add(new ServerThread(sockets.get(sockets.size() - 1)));
                outputStreams.get(outputStreams.size() - 1).writeInt(outputStreams.size() - 1);
            }
            for(ServerThread t:threads) {
                t.start();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    public void gestisciMessaggi() {
        String s1;
        int flag = 0;
        try {
            while (flag == 0) {
                s1 = inputStreams.get(0).readLine();
                if (s1 != null) {
                    System.out.println(s1);
                    outputStreams.get(1).writeBytes(s1);
                    outputStreams.get(1).flush();
                    flag = 1;
                } else {
                    System.out.println("porcoiddio!");
                    flag = 1;
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
