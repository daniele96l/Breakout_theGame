package sprites;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.*;
import help.Info;

import java.io.Serializable;

/**
 * @author Ligato
 *
 * Oggetto palla con cui il giocatore interagirà,
 */

public class Ball extends Sprite {
    private Texture palla;
    static private Vector2 positionBall;
    static private Vector2 speedBall;
    private Rectangle boundsBall;

    public Ball(){
        super(new Texture("pallone-1.png"));

        speedBall = new Vector2(0,-Info.velBall);
        palla=new Texture("pallone-1.png");
        positionBall = new Vector2(Info.larghezza/2-palla.getWidth()*Info.ballresize/2, Info.altezza/3);
        boundsBall = new Rectangle(positionBall.x, positionBall.y, palla.getWidth() * Info.ballresize,palla.getHeight()* Info.ballresize);

    }

    /**
     * Imposta la palla ad una posizione e velocità di defoult all'inizio del gioco oppure quando si perde
     */
    public void setDefaultState() {
        speedBall=new Vector2(0,-Info.velBall);
        positionBall = new Vector2(Info.larghezza/2-palla.getWidth()*Info.ballresize/2, Info.altezza/3);
       //  speedBall=new Vector2(-2,1);
        // positionBall = new Vector2(40, 630);
    }

    public Texture getPalla() { return palla; }

    public Vector2 getSpeedBall() {
        return speedBall;
    }

    public Rectangle getBoundsBall() {
        return boundsBall;
    }

    public void setPalla(Texture palla) {
        this.palla = palla;
    }

    public void setPositionBall() {
        this.positionBall = positionBall;
    }

    public void setSpeedBall(Vector2 speedBall) {
        this.speedBall = speedBall;
    }

    public void setBoundsBall(Rectangle boundsBall) {
        this.boundsBall = boundsBall;
    }

    public Vector2 getPositionBall() {
        return positionBall;
    }
}
