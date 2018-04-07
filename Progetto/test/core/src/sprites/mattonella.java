package sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;


public class mattonella extends Sprite {
    private Vector2 positionM;
    private Vector2 speed;
    private Rectangle bounds;

    public  mattonella(){
        super(new Texture("mattonalla.png"));
        positionM = new Vector2(0, 0);
        speed = new Vector2(0,0);
        bounds = new Rectangle(positionM.x, positionM.y, mattonella.this.getWidth(), mattonella.this.getHeight() );
        //il bound si riferiscono al rettangolo invisibile che costruirò intorno alla texture
        // l'altezza e la larghezza sono della texture
    }


    public Vector2 getPosition() {
        return positionM;
    }

    public Vector2 getSpeed() { return speed; }

    public Rectangle getBounds() { return bounds; }
}
