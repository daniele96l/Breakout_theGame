package sprites;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Scaling;
import com.mygdx.game.Info;

public class Brick extends Sprite{

    private Vector2 positionBrick;
    private Vector2 speed;
    private Rectangle boundsBrick;
    public  boolean eliminato;
    String nome;
    int intero;





    public Brick(int posX, int posY, String nome, int intero ){
        super(new Texture(nome));
        setAlpha(0.1f);
        positionBrick = new Vector2(posX, posY);
        speed = new Vector2(0,0);
        boundsBrick = new Rectangle(posX, posY, Brick.this.getWidth()* Info.brickresize, Brick.this.getHeight()* Info.brickresize );
        //il boundBrick si riferiscono al rettangolo invisibile che costruirò intorno alla texture
        //l'altezza e la larghezza sono della texture
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
