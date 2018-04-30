package ClientServer.Server;

import ClientServer.Dato;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client extends Thread
{
    private static Socket clientSocket = null;
    private static PrintStream os = null;
    private static DataInputStream is = null;
    private String line;
    private static BufferedReader inputLine = null;
    private static boolean closed = false;
    int flag = 0;


    public Client(Socket clientSocket)
    {
        int portNumber = 2222;
        String host = "localhost";

        try
        {
            if (flag == 0)
            {
                clientSocket = new Socket(host, portNumber);
                flag = 1;
            }
            inputLine = new BufferedReader(new InputStreamReader(System.in));
            os = new PrintStream(clientSocket.getOutputStream());
            is = new DataInputStream(clientSocket.getInputStream());
        }
        catch (UnknownHostException e)
        {
            System.err.println("Don'tknowabouthost" + host);
        }
        catch (IOException e)
        {
            System.err.println("Couldn't get I/O for the connection to the host"+ host);
        }


        if (clientSocket != null && os != null && is != null) {


        }
        String responseLine;
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        while ((br) != null)
        {
            try
            {


            }
            catch (Error e)
            {
                e.printStackTrace();
            }

        }

    }


    public void run()
    {

    }
}
