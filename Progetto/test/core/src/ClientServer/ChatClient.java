package ClientServer;

import com.badlogic.gdx.math.Vector2;
import sprites.Brick.AbstractBrick;
import java.io.DataInputStream;
import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import static java.lang.Thread.sleep;
public class ChatClient implements Runnable{

//Theclientsocket
        private static Socket clientSocket=null;
//Theoutputstream
        private static PrintStream os=null;
//Theinputstream
        private static DataInputStream is=null;
        private String line;
        private static BufferedReader inputLine=null;
        private static boolean closed=false;
        int flag=0;
        public float start_main(Dato dato){

//Thedefaultport.
        int portNumber=2222;
//Thedefaulthost.
        String host="localhost";

        /*
         *Openasocketonagivenhostandport.Openinputandoutputstreams.
         */
        try{
        if(flag==0){
        clientSocket=new Socket(host,portNumber);
        flag=1;}
//creasocketperparlarecolserver
        inputLine=new BufferedReader(new InputStreamReader(System.in));
        os=new PrintStream(clientSocket.getOutputStream());
        is=new DataInputStream(clientSocket.getInputStream());
        }catch(UnknownHostException e){
        System.err.println("Don'tknowabouthost"+host);
        }catch(IOException e){
        System.err.println("Couldn'tgetI/Ofortheconnectiontothehost"
        +host);
        }

        /*
         *Ifeverythinghasbeeninitializedthenwewanttowritesomedatatothe
         *socketwehaveopenedaconnectiontoontheportportNumber.
         */
        if(clientSocket!=null&&os!=null&&is!=null){

        /*Createathreadtoreadfromtheserver.*/
        new Thread(new ChatClient()).start();
        os.println(dato.getPalla().getPositionBall().x);
        os.println(dato.getPalla().getPositionBall().y);
        os.println(dato.getPaddle().getPosition().x);
        os.println(dato.getPaddle().getPosition().y);
        for(AbstractBrick abs:dato.getMattoncini())
        {
        os.println(abs.getPositionBrick().x);
        os.println(abs.getPositionBrick().y);

        }
        /*
         *Closetheoutputstream,closetheinputstream,closethesocket.
         */
//os.close();
//is.close();
//clientSocket.close();

        }
        return dato.getPalla().getPositionBall().x;
        }

        /*
         *Createathreadtoreadfromtheserver.(non-Javadoc)
         *
         *@seejava.lang.Runnable#run()
         */
        public void run(){
        /*
         *Keeponreadingfromthesockettillwereceive"Bye"fromthe
         *server.Oncewereceivedthatthenwewanttobreak.
         */

        String responseLine;
        BufferedReader br=new BufferedReader(new InputStreamReader(is));
        while((br)!=null){
//finchèquellocheleggoèdiversodanullolostampo
        try{
        System.out.println(br.readLine());
        br=null;
        }catch(IOException e){
        e.printStackTrace();
        }
        }
//closed=true;//chiudothread
        }
        }


