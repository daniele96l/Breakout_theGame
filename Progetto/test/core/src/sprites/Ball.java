package sprites;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.*;
import help.Info;


public class Ball extends Sprite{
    //commento inutile
    private Texture palla;
    static private Vector2 positionBall;
    static private Vector2 speedBall;
    private Rectangle boundsBall;

    public Ball(){
        super(new Texture("pallone-1.png"));
        positionBall = new Vector2(85, 400); //si vedono evidenti problemi di rimbalzo con y = 400  3 y = 200
        speedBall = new Vector2(0,-Info.velBall);
        palla=new Texture("pallone-1.png");
        boundsBall = new Rectangle(positionBall.x, positionBall.y, palla.getWidth() * Info.ballresize,palla.getHeight()* Info.ballresize);


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

    public void setPositionBall() {
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
