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
        positionBall = new Vector2(320, 400);
        speedBall = new Vector2(3,3);
        boundsBall = new Rectangle(positionBall.x, positionBall.y, 20,20);
    }

    public void update (float dt, mattonella mattonella, mattoncino mattoncino){
        Boolean topCollide = false;
        positionBall.add(speedBall.x *dt, speedBall.y*dt);
        boundsBall.setPosition(positionBall.x, positionBall.y);

        if(positionBall.x > Info.larghezza- 30) //controllo che rimbalzi a destra
            speedBall.set(-3, speedBall.y);
        if(positionBall.y > Info.altezza -30) //controllo che rimbalzi su
            speedBall.set(speedBall.x,-3);
        if(positionBall.x < 0)
            speedBall.set(3, speedBall.y); //controllo che rimbalzi a sinistra

        if(mattonella.collides(boundsBall)) //controllo che collida con la mattonella
            speedBall.set(speedBall.x, -speedBall.y);

        ///////////////////////////////////////////////////////////////////
        //è necessario capire se sta sbattendo con i lati (sinistra e destra), o con il top e bottom del mattoncino
        //Dato che a seconda di dove sbatte si comporta diversamente
        ///////////////////////////////////////////////////////////////////////////////

        /*if(mattoncino.collidesSide(boundsBall)){
            mattoncino.delete();
            speedBall.set(-speedBall.x, speedBall.y);
            topCollide = true;
        }

        if(!topCollide)
        if(mattoncino.collidesTopBottom(boundsBall)){ //controllo che collida col mattoncino
            mattoncino.delete();            //quando abbiamo tanti mattoncini bisogna utilizzare un ciclo con un Arraylist
            speedBall.set(speedBall.x, -speedBall.y );
        } */
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
