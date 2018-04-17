package sprites.Brick;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import help.Info;

public  abstract class AbstractBrick  extends Sprite{

    private Vector2 positionBrick;
    private Vector2 speed;
    private Rectangle boundsBrick;
    public  boolean eliminato;
    String nome;
    int durezza;

    public AbstractBrick(int posX, int posY, String nome){
       super(new Texture(nome));
    }

    public int getDurezza() {
        return durezza;
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

    public void setSpeed(Vector2 speed) {
        this.speed = speed;
    }

    public void setBoundsBrick(Rectangle boundsBrick) {
        this.boundsBrick = boundsBrick;
    }

    public void setEliminato(boolean eliminato) {
        this.eliminato = eliminato;
    }


    public Vector2 getSpeed() {
        return speed;
    }

    public Rectangle getBoundsBrick() {
        return boundsBrick;
    }

    public boolean isEliminato() {
        return eliminato;
    }
}
