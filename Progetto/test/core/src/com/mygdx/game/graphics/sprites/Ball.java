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

    /**
     * Questo metodo restituisce la texture della palla
     *
     * @return texture della palla
     */
    public Texture getPalla() { return palla; }

    /**
     * Questo metodo restituisce la velocità della palla
     *
     * @return velocità della palla
     */
    public Vector2 getSpeedBall() {
        return speedBall;
    }

    /**
     * Questo metodo restituisce i contorni dell'oggetto palla
     * @return contorni dell'oggetto palla
     */
    public Rectangle getBoundsBall() {
        return boundsBall;
    }

    /**
     * Questo metodo imposta la texture della palla
     *
     * @param palla texture che si vuole applicare alla palla
     */
    public void setPalla(Texture palla) {
        this.palla = palla;
    }

    /**
     * Questo metodo imposta la posizione di default della palla al centro dello schermo
     */
    public void setPositionBall() {
        this.positionBall = positionBall;
    }

    /**
     * Questo metodo imposta la velocità x e y della palla
     *
     * @param speedBall vettore di due dimensioni (x e y) per la velocità della palla
     */
    public void setSpeedBall(Vector2 speedBall) {
        this.speedBall = speedBall;
    }

    public void setBoundsBall(Rectangle boundsBall) {
        this.boundsBall = boundsBall;
    }

    /**
     * Questo metodo restituisce la posizione x e y dell'oggetto palla
     *
     * @return posizione x e y dell'oggetto palla
     */
    public Vector2 getPositionBall() {
        return positionBall;
    }
}
