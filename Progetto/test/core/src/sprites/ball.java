package sprites;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.*;
import com.mygdx.game.Info;


public class ball  extends Sprite{

    private Texture palla;
    private Vector2 positionBall;
    private Vector2 speedBall;
    private Rectangle boundsBall;

    public ball(){
        super(new Texture("pallone-1.png"));
        positionBall = new Vector2(700, 580);
        speedBall = new Vector2(3,3);
        boundsBall = new Rectangle(positionBall.x, positionBall.y, 20,20);
    }



    public Texture getPalla() { return palla; }

    public Vector2 getSpeedBall() {
        return speedBall;
    }

    public Rectangle getBoundsBall() {
        return boundsBall;
    }

    public void setPalla(Texture palla) {
        this.palla = palla;
    }

    public void setPositionBall(Vector2 positionBall) {
        this.positionBall = positionBall;
    }

    public void setSpeedBall(Vector2 speedBall) {
        this.speedBall = speedBall;
    }

    public void setBoundsBall(Rectangle boundsBall) {
        this.boundsBall = boundsBall;
    }

    public Vector2 getPositionBall() {
        return positionBall;
    }
}
