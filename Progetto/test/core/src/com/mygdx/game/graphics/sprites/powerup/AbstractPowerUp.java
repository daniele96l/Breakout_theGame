package com.mygdx.game.graphics.sprites.powerup;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.logic.Player.Player;
import com.mygdx.game.help.Info;
import com.mygdx.game.graphics.sprites.Ball;
import com.mygdx.game.graphics.sprites.Paddle;

/**
 * Questa classe definisce l'oggetto astratto PowerUp, vale a dire un potenziamento/depotenziamento
 * (es. aggiunge/toglie vite al giocatore, allarga/diminuisce la larghezza della paddle)
 * che cade dal mattonicino appena colpito dal giocatore
 *
 * @author Alberto Schillaci
 */
public abstract class AbstractPowerUp extends Sprite implements PowerUp {
    private Vector2 position;
    private Vector2 speed;
    private Rectangle bounds;
    String sound;

    AbstractPowerUp(String image, int posX, int posY) {
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


    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getSpeed() {
        return speed;
    }


    public String getSound(){
        return sound;
    }

}
