package com.mygdx.game.graphics.sprites;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.*;
import com.mygdx.game.help.Info;

/**
 * La classe Ball contiene tutte le informazioni e variabili della palla bersaglio
 * per corretto il funzionamento del gioco, quali per esempio la sua texture, il suo
 * suo vettore posizione e la sua velocità
 *
 * @author Daniele Ligato
 */

public class Ball extends Sprite {
    private Texture palla;
    static private Vector2 positionBall;
    static private Vector2 speedBall;
    private Rectangle boundsBall;

    public Ball(){
        super(new Texture("pallone-1.png"));

        speedBall = new Vector2(0,-Info.getInstance().getVelBall());
        palla=new Texture("pallone-1.png");
        positionBall = new Vector2(Info.getInstance().getLarghezza()/2-palla.getWidth()*Info.getInstance().getBallresize()/2, Info.getInstance().getAltezza()/3);
        boundsBall = new Rectangle(positionBall.x, positionBall.y, palla.getWidth() * Info.getInstance().getBallresize(),palla.getHeight()* Info.getInstance().getBallresize());

    }

    /**
     * Questo metodo posiziona la palla al centro dello schermo (posizione di default)
     * all'inizio di ogni nuova partita o quando il giocatore perde
     */
    public void setDefaultState() {
        speedBall=new Vector2(0,-Info.getInstance().getVelBall());
        positionBall = new Vector2(Info.getInstance().getLarghezza()/2-palla.getWidth()*Info.getInstance().getBallresize()/2, Info.getInstance().getAltezza()/3);
       //  speedBall=new Vector2(-2,1);
        // positionBall = new Vector2(40, 630);
    }

    public Vector2 getSpeedBall() {
        return speedBall;
    }

    public Rectangle getBoundsBall() {
        return boundsBall;
    }

    public void setSpeedBall(Vector2 speedBall) {
        this.speedBall = speedBall;
    }

    public Vector2 getPositionBall() {
        return positionBall;
    }
}
