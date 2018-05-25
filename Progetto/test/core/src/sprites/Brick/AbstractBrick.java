package sprites.Brick;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import help.Info;
import sprites.powerup.PowerUp;

import java.io.Serializable;

/**
 * @author ligato
 * Classe Astratta del mattoncino
 */
public  abstract class AbstractBrick  extends Sprite {

    protected Vector2 positionBrick;
    protected Rectangle boundsBrick;
    protected boolean eliminato;
    protected PowerUp powerUp;
    protected boolean hasPowerUp;

    public AbstractBrick(int posX, int posY, String immagine){
        super(new Texture(immagine));
        hasPowerUp=false;
        positionBrick = new Vector2(posX, posY);
        boundsBrick = new Rectangle(posX, posY, this.getWidth()* Info.brickresize, this.getHeight()* Info.brickresize );
    }

    /**
     * Assegna il power up al mattoncino e setta il boolean a True
     * @param powerUp potenziamento che cadrà dal mattoncino
     */
    public void setPowerUp(PowerUp powerUp) {
        this.powerUp = powerUp;
        hasPowerUp=true;
    }

    public boolean hasPowerUp() {
        return hasPowerUp;
    }

    public PowerUp getPowerUp() {
        return powerUp;
    }

    public Vector2 getPositionBrick() {
        return positionBrick;
    }

    public void delete(){
        eliminato = true;
    }

    public void setPositionBrick(Vector2 positionBrick) {
        this.positionBrick = positionBrick;
    }

    public void setBoundsBrick(Rectangle boundsBrick) {
        this.boundsBrick = boundsBrick;
    }

    public void setEliminato(boolean eliminato) {
        this.eliminato = eliminato;
    }

    public Rectangle getBoundsBrick() {
        return boundsBrick;
    }

    public boolean isEliminato() {
        return eliminato;
    }
}
