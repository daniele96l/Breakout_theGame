package sprites;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.*;
import com.mygdx.game.Info;


public class ball  extends Sprite{

    private Texture palla;
    private Vector2 position;
    private Vector2 speed;
    private Rectangle bounsBall;

    public ball(){
        super(new Texture("pallone-1.png"));
        position = new Vector2(700, 580);
        speed = new Vector2(3,3);
        bounsBall = new Rectangle(position.x, position.y, 20,20);
    }

    public void update (float dt, mattonella mattonella, mattoncino mattoncino){
        Boolean topCollide = false;
        position.add(speed.x *dt,speed.y*dt);
        bounsBall.setPosition(position.x, position.y);

        if(position.x > Info.larghezza- 30) //controllo che rimbalzi a destra
            speed.set(-3,speed.y);
        if(position.y > Info.altezza -30) //controllo che rimbalzi su
            speed.set(speed.x,-3);
        if(position.x < 0)
            speed.set(3, speed.y); //controllo che rimbalzi a sinistra

        if(mattonella.collides(bounsBall)) //controllo che collida con la mattonella
            speed.set(speed.x, -speed.y);

        ///////////////////////////////////////////////////////////////////
        //è necessario capire se sta sbattendo con i lati (sinistra e destra), o con il top e bottom del mattoncino
        //Dato che a seconda di dove sbatte si comporta diversamente
        ///////////////////////////////////////////////////////////////////////////////

        if(mattoncino.collidesSide(bounsBall)){
            mattoncino.delete();
            speed.set(-speed.x, speed.y);
            topCollide = true;
        }

        if(!topCollide)
        if(mattoncino.collidesTopBottom(bounsBall)){ //controllo che collida col mattoncino
            mattoncino.delete();            //quando abbiamo tanti mattoncini bisogna utilizzare un ciclo con un Arraylist
            speed.set(speed.x, -speed.y );
        }
    }

    public Vector2 getPosition() {
        return position;
    }
}
