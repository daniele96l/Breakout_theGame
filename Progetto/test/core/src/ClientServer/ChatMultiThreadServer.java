
package ClientServer;

import java.io.DataInputStream;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;

/*A chat server that delivers public and private messages.*/
        /*SERVERRRRRRRRRR*/
        public class ChatMultiThreadServer
{

        //Theserversocket.
        private static ServerSocket serverSocket=null;
        //Theclientsocket.
        private static Socket clientSocket=null;

        //This  chat    servercanacceptuptomaxClientsCountclients'connections.
        private static final int maxClientsCount=10;
//ARRAYDICLIENT
        private static final clientThread[]threads=new clientThread[maxClientsCount];
        public static void main(String[]args)
        {



        //Thedefaultportnumber.
        int portNumber=2222;

        /*
         *OpenaserversocketontheportNumber(default2222).Notethatwecan
         *notchooseaportlessthan1023ifwearenotprivilegedusers(root).
         */
        try{
//Istanzia server suporta
        serverSocket=new ServerSocket(portNumber);
        }catch(IOException e){
        System.out.println(e.getMessage());
        }

        /*
         *Create a client socket for each connection and pass it to a new  client
         *thread.
         */
        while(true){
        try{
//ILsocketdelclientassumeilvalorediciòcheilserverriceve,quindiilsocketdelrichiedente
        clientSocket=serverSocket.accept();
        int i=0;
        for(i=0;i<maxClientsCount;i++){
//seilsocketènulloistanziounnuovoclientinquellaposizionedell'arrayelorunno
        if(threads[i]==null){
        (threads[i]=new clientThread(clientSocket,threads)).start();
        break;
        }
        }
//sei=10nonaccettopiùrichieste
        if(i==maxClientsCount){
        PrintStream os=new PrintStream(clientSocket.getOutputStream());
        os.println("Servertoobusy.Trylater.");
        os.close();
        clientSocket.close();
        }
        }catch(IOException e){
        System.out.println(e);
        }
        }
        }
        }

        /*
         *Thechatclientthread.Thisclientthreadopenstheinputandtheoutput
         *streamsforaparticularclient,asktheclient'sname,informsallthe
         *clientsconnectedtotheserveraboutthefactthatanewclienthasjoined
         *thechatroom,andaslongasitreceivedata,echosthatdatabacktoall
         *otherclients.Thethreadbroadcasttheincomingmessagestoallclientsand
         *routestheprivatemessagetotheparticularclient.Whenaclientleavesthe
         *chatroomthisthreadinformsalsoalltheclientsaboutthatandterminates.
         */



        class clientThread extends Thread{

        private String clientName=null;//nomeclient
        private DataInputStream is=null;
        private PrintStream os=null;
        private Socket clientSocket;
        private final clientThread[]threads;
        private int maxClientsCount;

//costruttorecheaccettailsocketetuttiglialtriClientThreadaperti

        public clientThread(Socket clientSocket,clientThread[]threads){
        this.clientSocket=clientSocket;
        this.threads=threads;
        maxClientsCount=threads.length;
        }

        public void run(){
        int count=0;
        int maxClientsCount=this.maxClientsCount;
        clientThread[]threads=this.threads;

        try{
        /*
         *Createinputandoutputstreamsforthisclient.
         */

        is=new DataInputStream(clientSocket.getInputStream());
        os=new PrintStream(clientSocket.getOutputStream());

        String name;

synchronized(this){
//permettel'accessoall'internodelbloccosoloadunthreadallavolta
//inmodotaledanonmodificarecontemporaneamenteidati
        for(int i=0;i<maxClientsCount;i++){
        if(threads[i]!=null&&threads[i]==this){

//se il thread non è vuoto ed è uguale a questo thread gli assegna il nome dato alclient
        clientName="@"+count;
        count++;
        break;
        }
        }
        for(int i=0;i<maxClientsCount;i++){
//seilthreadnonèpresentenell'elencostampailbenvenutoalnuovoclient
        if(threads[i]!=null&&threads[i]!=this){
        }
        }
        }
        /*Starttheconversation.*/
        while(true){
//Leggeinputstreamquellochearriva
        String line=is.readLine();
        System.out.println(line);
        os.println(line);
        //if(line.startsWith("/quit")){
        //seilmessaggioiniziaconquitchiudelalettura
//break;
//}
        /*Ifthemessageisprivatesentittothegivenclient.*/
        if(line.startsWith("@")){
//seilmessaggioiniziacon@mandoilmessaggioadunsoloclient
//falosplitconlospazioledueparoleinwordssonoilnomeclientacuièdiretto
//eilmessaggio
        String[]words=line.split("\\s",2);
//selestringhesonoalmeno2elasecondanonènulla
/*if(words.length>1&&words[1]!=null){
//toglieglispazialmessaggio
words[1]=words[1].trim();
if(!words[1].isEmpty()){
//seilmessaggiononèvuotoentrainsync
synchronized(this){
for(inti=0;i<maxClientsCount;i++){
//iteranell'arraydithreadpercercarequelloacuièdirettoilmex
if(threads[i]!=null&&threads[i]!=this
&&threads[i].clientName!=null
&&threads[i].clientName.equals(words[0])){
//eglielomando
threads[i].os.println("<"+name+">"+words[1]);

this.os.println(">"+name+">"+words[1]);
break;
}
}
}
}
}*/
        }else{
        /*Themessageispublic,broadcastittoallotherclients.*/
synchronized(this){
        for(int i=0;i<maxClientsCount;i++){
        if(threads[i]!=null&&threads[i].clientName!=null){
//threads[i].os.println("<"+clientName+">"+line);
        }
        }
        }
        }
        }//seèstatodigitatoquitescedalwhileattraversoilbreak
/*synchronized(this){
for(inti=0;i<maxClientsCount;i++){
if(threads[i]!=null&&threads[i]!=this
&&threads[i].clientName!=null){
threads[i].os.println("***Theuser"+name
+"isleavingthechatroom!!!***");
}
}
}*/
//os.println("***Bye"+name+"***");

        /*
         *Cleanup.Setthecurrentthreadvariabletonullsothatanewclient
         *couldbeacceptedbytheserver.
         */
/*synchronized(this){
//pulischelaposizionenell'array
for(inti=0;i<maxClientsCount;i++){
if(threads[i]==this){
threads[i]=null;
}
}
}*/
        /*
         *Closetheoutputstream,closetheinputstream,closethesocket.
         */
//is.close();
//os.close();
//clientSocket.close();
        }catch(IOException e){
        }
        }
        }
