package sprites.Brick;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import help.Info;

public class Brick extends AbstractBrick{

    public Brick(int posX, int posY){
        super(posX, posY, "normalBrick.jpg");
        this.durezza = 0;
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
