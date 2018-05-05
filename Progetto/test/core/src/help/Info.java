package help;

import com.badlogic.gdx.graphics.Texture;

public class Info {
    public static int altezza = 850;
    public static int larghezza = 800;
    public static int velBall=8;
    public static int dt = 1;
    public static float paddleresize = 0.5f;
    public static float brickresize = 0.8f;
    public static float ballresize = 0.8f;
    public static int brickGapX=10;
    public static int brickGapY=7;
    public static int defaultLivesNum=3;
    public static int hudHeight=80;
    public static int powerUpSpeed=4;
    public static float powerUpResize=0.5f;
    public static int powerUpChance=5; //Probabilità da 1 a 10 che in un mattoncino ci sia un power up
    public static int numeroPowerUp=1; //Numero di power up esistenti (da aggiornare se se ne crea uno nuovo)
    public static int powerUpWidth=50;
    public static int powerUpHeight=45;

    public static int getBrickWidth () {
        Texture brick=new Texture("normalBrick.jpg");
        int brickWidth=(int)(brick.getWidth()*brickresize);
        return brickWidth;
    }

    public static int getBrickHeight() {
        Texture brick=new Texture("normalBrick.jpg");
        int brickHeight=(int)(brick.getHeight()*brickresize);
        return brickHeight;
    }
}
