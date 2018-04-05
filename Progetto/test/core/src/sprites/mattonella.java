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

    public void update (float dt){ //muovo la mattonella

        if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)){
            if(positionM.x > 0) { //controllo il range in cui la mattonella si può muovere
                positionM.add(-30, 0);
                bounds.setPosition(positionM.x, positionM.y);
            }
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)){
            if(positionM.x < 800 - getTexture().getWidth()) {
                positionM.add(30, 0);//controllo il range in cui la mattonella si può muovere
                bounds.setPosition(positionM.x, positionM.y);
            }
        }
    }

    public boolean collides (Rectangle boundsBall){
        if(boundsBall.y < 30) //ovvero se la palla scende sotto il bordo superiose e colpisce il lato della mattonella  non rimalza ma va dritta giu
            return false;  //dato che ha mancato la parte superiore piana è impossibile che venga rimbalzata su
                            //serve anche ad evitare un bug che faceva entrare la pallina dentro la mattonella
        return boundsBall.overlaps(bounds); //la funzione che controllerà se la pallina tocca la mattonella
    }

    public Vector2 getPosition() {
        return positionM;
    }
}
