package sprites.powerup;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Player.Player;
import help.Info;
import sprites.Ball;
import sprites.Paddle;

import java.io.Serializable;

/**
 * Questa classe definisce l'oggetto astratto PowerUp, vale a dire un potenziamento/depotenziamento
 * (es. aggiunge/toglie vite al giocatore, allarga/diminuisce la larghezza della paddle)
 * che cade dal mattonicino appena colpito dal giocatore
 *
 * @author Alberto Schillaci
 */
public abstract class AbstractPowerUp extends Sprite implements PowerUp {
    protected Vector2 position;
    protected Vector2 speed;
    protected Rectangle bounds;
    protected String sound;

    public AbstractPowerUp(String image, int posX, int posY) {
        super(new Texture(image));
        this.position=new Vector2(posX, posY);
        this.speed=new Vector2(0, -Info.getInstance().getPowerUpSpeed());
        this.bounds=new Rectangle(posX,posY,this.getWidth()*Info.getInstance().getPowerUpResize(), this.getHeight()*Info.getInstance().getPowerUpResize());
    }

    /**
     * Questo metodo rende effettivo l'effetto del PowerUp
     * @param player il giocatore su cui il PowerUp ha effetto
     * @param paddle la paddle che ha preso il PowerUp
     * @param palla la palla che ha colpito il mattoncino contenente il PowerUp
     */
    public abstract void effect(Player player, Paddle paddle, Ball palla);

    public Rectangle getBounds() {
        return bounds;
    }


    /**
     * Questo metodo ritorna il vettore posizione (x e y) del PowerUp
     * @return il vettore posizione (x e y) del PowerUp
     */
    public Vector2 getPosition() {
        return position;
    }

    /**
     * Questo metodo ritorna il vettore velocità (x e y) con cui
     * cade il PowerUp dal mattonicino appena colpito
     * @return il vettore velocità (x e y) con cui cade il PowerUp
     */
    public Vector2 getSpeed() {
        return speed;
    }


    /**
     * Questo metodo ritorna la stringa del nome file
     * che indica l'effetto sonoro associato a un determinato PowerUp
     * @return la stringa del nome file che indica l'effetto sonoro associato a quel determinato PowerUp
     */
    public String getSound(){
        return sound;
    }

}
