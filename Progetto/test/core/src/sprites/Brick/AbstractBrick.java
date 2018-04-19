package sprites.Brick;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import help.Info;

public  abstract class AbstractBrick  extends Sprite{

    protected Vector2 positionBrick;
    protected Rectangle boundsBrick;
    protected boolean eliminato;
    protected int durezza;

    public AbstractBrick(int posX, int posY, String nome){
        super(new Texture(nome));
        positionBrick = new Vector2(posX, posY);
        boundsBrick = new Rectangle(posX, posY, this.getWidth()* Info.brickresize, this.getHeight()* Info.brickresize );
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
