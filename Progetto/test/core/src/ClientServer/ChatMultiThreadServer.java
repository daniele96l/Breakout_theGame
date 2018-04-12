package ClientServer;

import java.io.DataInputStream;
import java.io.PrintStream;
import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;

/*
 * A chat server that delivers public and private messages.
 */
/*SERVERRRRRRRRRR*/
public class ChatMultiThreadServer {

    // The server socket.
    private static ServerSocket serverSocket = null;
    // The client socket.
    private static Socket clientSocket = null;

    // This chat server can accept up to maxClientsCount clients' connections.
    private static final int maxClientsCount = 10;
    //ARRAY DI CLIENT
    private static final clientThread[] threads = new clientThread[maxClientsCount];

    public static void main(String[] args) {



        // The default port number.
        int portNumber = 2222;

        /*
         * Open a server socket on the portNumber (default 2222). Note that we can
         * not choose a port less than 1023 if we are not privileged users (root).
         */
        try {
            //Istanzia server su porta
            serverSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
            System.out.println(e);
        }

        /*
         * Create a client socket for each connection and pass it to a new client
         * thread.
         */
        while (true) {
            try {
                //IL socket del client assume il valore di ciò che il server riceve, quindi il socket del richiedente
                clientSocket = serverSocket.accept();
                int i = 0;
                for (i = 0; i < maxClientsCount; i++) {
                    // se il socket è nullo istanzio un nuovo client in quella posizione dell'array e lo runno
                    if (threads[i] == null) {
                        (threads[i] = new clientThread(clientSocket, threads)).start();
                        break;
                    }
                }
                //se i = 10 non accetto più richieste
                if (i == maxClientsCount) {
                    PrintStream os = new PrintStream(clientSocket.getOutputStream());
                    os.println("Server too busy. Try later.");
                    os.close();
                    clientSocket.close();
                }
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }
}

        /*
         * The chat client thread. This client thread opens the input and the output
         * streams for a particular client, ask the client's name, informs all the
         * clients connected to the server about the fact that a new client has joined
         * the chat room, and as long as it receive data, echos that data back to all
         * other clients. The thread broadcast the incoming messages to all clients and
         * routes the private message to the particular client. When a client leaves the
         * chat room this thread informs also all the clients about that and terminates.
         */



class clientThread extends Thread {

    private String clientName = null; //nome client
    private DataInputStream is = null;
    private PrintStream os = null;
    private Socket clientSocket = null;
    private final clientThread[] threads;
    private int maxClientsCount;

    //costruttore che accetta il socket e tutti gli altri Client Thread aperti

    public clientThread(Socket clientSocket, clientThread[] threads) {
        this.clientSocket = clientSocket;
        this.threads = threads;
        maxClientsCount = threads.length;
    }

    public void run() {
        int maxClientsCount = this.maxClientsCount;
        clientThread[] threads = this.threads;

        try {
            /*
             * Create input and output streams for this client.
             */

            is = new DataInputStream(clientSocket.getInputStream());
            os = new PrintStream(clientSocket.getOutputStream());
            String name;
            while (true) {
                // chiedo al client di inserire il nome
                // acquisisco il nome utente senza spazi
                name = "giocatore1";
                if (name.indexOf('@') == -1) {
                    break;
                } else {
                    os.println("The name should not contain '@' character.");
                }
            }

            /* Welcome the new the client. */
            os.println("Welcome " + name
                    + " to our chat room.\nTo leave enter /quit in a new line.");
            synchronized (this) {
                // permette l'accesso all'interno del blocco solo ad un thread alla volta
                //in modo tale da non modificare contemporaneamente i dati
                for (int i = 0; i < maxClientsCount; i++) {
                    if (threads[i] != null && threads[i] == this) {
                        //se il thread non è vuoto ed è uguale a questo thread gli assegna il nome dato al client
                        clientName = "@" + name;
                        break;
                    }
                }
                for (int i = 0; i < maxClientsCount; i++) {
                    // se il thread non è presente nell'elenco stampa il benvenuto al nuovo client
                    if (threads[i] != null && threads[i] != this) {
                        threads[i].os.println("*** A new user " + name
                                + " entered the chat room !!! ***");
                    }
                }
            }
            /* Start the conversation. */
            while (true) {
                // Legge input stream quello che arriva
                String line = is.readLine();
                System.out.println(line);
               // if (line.startsWith("/quit")) {
                    // se il messaggio inizia con quit chiude la lettura
                 //   break;
                //}
                /* If the message is private sent it to the given client. */
                if (line.startsWith("@")) {
                    // se il messaggio inizia con @ mando il messaggio ad un solo client
                    // fa lo split con lo spazio le due parole in words sono il nome client a cui è diretto
                    // e il messaggio
                    String[] words = line.split("\\s", 2);
                    //se le stringhe sono almeno 2 e la seconda non è nulla
                   /* if (words.length > 1 && words[1] != null) {
                        // toglie gli spazi al messaggio
                        words[1] = words[1].trim();
                        if (!words[1].isEmpty()) {
                            // se il messaggio non è vuoto entra in sync
                            synchronized (this) {
                                for (int i = 0; i < maxClientsCount; i++) {
                                    //itera nell'array di thread per cercare quello a cui è diretto il mex
                                    if (threads[i] != null && threads[i] != this
                                            && threads[i].clientName != null
                                            && threads[i].clientName.equals(words[0])) {
                                        // e glielo mando
                                        threads[i].os.println("<" + name + "> " + words[1]);

                                        this.os.println(">" + name + "> " + words[1]);
                                        break;
                                    }
                                }
                            }
                        }
                    }*/
                } else {
                    /* The message is public, broadcast it to all other clients. */
                    synchronized (this) {
                        for (int i = 0; i < maxClientsCount; i++) {
                            if (threads[i] != null && threads[i].clientName != null) {
                                threads[i].os.println("<" + name + "> " + line);
                            }
                        }
                    }
                }
            } // se è stato digitato quit esce dal while attraverso il break
           /* synchronized (this) {
                for (int i = 0; i < maxClientsCount; i++) {
                    if (threads[i] != null && threads[i] != this
                            && threads[i].clientName != null) {
                        threads[i].os.println("*** The user " + name
                                + " is leaving the chat room !!! ***");
                    }
                }
            }*/
           // os.println("*** Bye " + name + " ***");

            /*
             * Clean up. Set the current thread variable to null so that a new client
             * could be accepted by the server.
             */
           /* synchronized (this) {
                //pulische la posizione nell'array
                for (int i = 0; i < maxClientsCount; i++) {
                    if (threads[i] == this) {
                        threads[i] = null;
                    }
                }
            }*/
            /*
             * Close the output stream, close the input stream, close the socket.
             */
           // is.close();
            //os.close();
            //clientSocket.close();
        } catch (IOException e) {
        }
    }
}

