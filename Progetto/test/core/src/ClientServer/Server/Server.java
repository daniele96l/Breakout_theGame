package ClientServer.Server;

import ClientServer.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
public class Server
{
    private ServerSocket serverSocket;
    private Socket socket = null;
    private int port = 2222;
    private ObjectInputStream objis;
    private ObjectOutputStream objos;
    private static final Client[]threads=new Client[4];

    public Server ()
    {
        try
        {
            serverSocket = new ServerSocket(port);



        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        while(true)
        {
            try
            {
                socket = serverSocket.accept();
                for(int i=0;i<4;i++)
                {
                    if(threads[i]==null)
                    {
                        (threads[i]=new Client(socket)).start();
                        break;
                    }
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

        }


    }



}
