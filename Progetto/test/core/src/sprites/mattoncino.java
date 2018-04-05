package sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class mattoncino  extends Sprite{

    private Vector2 positionB;
    private Vector2 speed;
    private Rectangle boundsBrick;
    public  boolean eliminato;

    public mattoncino(int posX, int posY){
        super(new Texture("brick.jpg"));
        positionB = new Vector2(posX, posY);
        speed = new Vector2(0,0);
        boundsBrick = new Rectangle(posX, posY, mattoncino.this.getWidth(), mattoncino.this.getHeight() );
        //il boundBrick si riferiscono al rettangolo invisibile che costruirò intorno alla texture
        //l'altezza e la larghezza sono della texture
    }

    public void update (float dt){
        if(eliminato == true){
            //per eliminare il mattoncino dallo schermo lo sposto semplicemente molto lontano
            boundsBrick.setPosition(10000,10000); //sposto il mattoncino (fisico) verso infinito
            positionB.set(1000, 1000);  //sposto il mattoncino (texture)verso infinito
        }
    }

    public boolean collidesTopBottom (Rectangle boundBall){
        /////Controllo dove avviene l'impatto
        if(boundBall.overlaps(boundsBrick)) {

            if (boundBall.y +1  >= boundsBrick.y + boundsBrick.height){ // il numero 1 è messo li a cazzo
                //impact from top
                return true;
            }
            if(boundBall.y  <= boundsBrick.y){
                //impact from the bottom
                return true;
            }
        }
        return false;
    }

    public boolean collidesSide(Rectangle boundBall) {
        /////Controllo dove avviene l'impatto
        if (boundBall.overlaps(boundsBrick)) {
            if (boundBall.x <= boundsBrick.x) { //impact from the left
                return true;
            }
            if (boundBall.x + 4 >= boundsBrick.x + boundsBrick.width) { //impact from the right
                return true; //il numero 4 è messo li a cazzo ma senza non va
            }
        }
        return false;
    }

    public Vector2 getPosition() {
        return positionB;
    }

    public void delete(){
      eliminato = true;
    }
}
