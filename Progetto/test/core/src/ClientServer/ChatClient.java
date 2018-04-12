package ClientServer;

import com.badlogic.gdx.math.Vector2;

import java.io.DataInputStream;
import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClient implements Runnable {

    // The client socket
    private static Socket clientSocket = null;
    // The output stream
    private static PrintStream os = null;
    // The input stream
    private static DataInputStream is = null;

    private static BufferedReader inputLine = null;
    private static boolean closed = false;
    int flag = 0;
    public  void start_main(Vector2 dato) {

        // The default port.
        int portNumber = 2222;
        // The default host.
        String host = "localhost";

        /*
         * Open a socket on a given host and port. Open input and output streams.
         */
        try {
            if(flag==0){
            clientSocket = new Socket(host, portNumber);
            flag=1;}
            //crea socket per parlare col server
            inputLine = new BufferedReader(new InputStreamReader(System.in));
            os = new PrintStream(clientSocket.getOutputStream());
            is = new DataInputStream(clientSocket.getInputStream());
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + host);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to the host "
                    + host);
        }

        /*
         * If everything has been initialized then we want to write some data to the
         * socket we have opened a connection to on the port portNumber.
         */
        if (clientSocket != null && os != null && is != null) {

                /* Create a thread to read from the server. */
                new Thread(new ChatClient()).start();
                    //finchè non si chiude legge i messaggi ma toglie gli spazi
                    if((dato.x>0 && dato.y>0) || (dato.x<800 && dato.y<850))
                    os.println("x: "+dato.x+" y: "+dato.y);
                /*
                 * Close the output stream, close the input stream, close the socket.
                 */
              //  os.close();
                //is.close();
                //clientSocket.close();

        }
    }

    /*
     * Create a thread to read from the server. (non-Javadoc)
     *
     * @see java.lang.Runnable#run()
     */
    public void run() {
        /*
         * Keep on reading from the socket till we receive "Bye" from the
         * server. Once we received that then we want to break.
         */
        String responseLine;
        try {
            while ((responseLine = is.readLine()) != null) {
                // finchè quello che leggo è diverso da nullo lo stampo
                System.out.println(responseLine);
                if (responseLine.indexOf("*** Bye") != -1)
                    // se quello che ricevo è bye ritorno con-1 ed esco dal ciclo
                    break;
            }
            closed = true; //chiudo thread
        } catch (IOException e) {
            System.err.println("IOException:  " + e);
        }
    }
}

