package sprites.Brick;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import help.Info;

public class HardBrick extends AbstractBrick {

    private Vector2 positionBrick;
    private Vector2 speed;
    private Rectangle boundsBrick;
    public  boolean eliminato;
    String nome;
    int durezza;


    public HardBrick(int posX, int posY, String nome ){
        super(posX, posY, nome);
        this.durezza = 1;
        positionBrick = new Vector2(posX, posY);
        speed = new Vector2(0,0);
        boundsBrick = new Rectangle(posX, posY, HardBrick.this.getWidth()* Info.brickresize, HardBrick.this.getHeight()* Info.brickresize );
        //il boundBrick si riferiscono al rettangolo invisibile che costruirò intorno alla texture
        //l'altezza e la larghezza sono della texture
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
