package help;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

/**
 * @author Ligato
 * Classe che contiene le costanti che verranno utilizzare durante il gioco
 */
public class Info {
    public static int altezza = 720;
    public static int larghezza = 1280;
    public static int velBall=9;
    public static int velPaddle = 13;
    public static int dt = 1;
    public static ArrayList<Float> paddleresizex = new ArrayList<Float>();
    public static float paddleresize = 0.5f;
    public static float brickresize = 0.5f;
    public static float ballresize = 0.8f;
    public static int brickGapX=5;
    public static int brickGapY=3;
    public static int defaultLivesNum=5;
    public static int hudHeight=80;
    public static int powerUpSpeed=3;
    public static float powerUpResize=0.5f;
    public static float powerUpChance=2; //Probabilità da 1 a 10 che in un mattoncino ci sia un power up
    public static int numeroPowerUp=4; //Numero di power up esistenti (da aggiornare se se ne crea uno nuovo)
    public static int powerUpWidth=50;
    public static int powerUpHeight=45;
    public static int durataPowerUp = 10000;

    public static int getVelPaddle() {
        return velPaddle;
    }

    /**
     * classe che ritorna..
     * @return la larghezza del mattoncino in funzione della texture
     */
    public static int getBrickWidth () {
        Texture brick=new Texture("normalBrick.jpg");
        int brickWidth=(int)(brick.getWidth()*brickresize);
        return brickWidth;
    }

    /**
     * classe che ritorna..
     * @return l altezza del mattoncino in funzione della texture
     */
    public static int getBrickHeight() {
        Texture brick=new Texture("normalBrick.jpg");
        int brickHeight=(int)(brick.getHeight()*brickresize);
        return brickHeight;
    }

}
