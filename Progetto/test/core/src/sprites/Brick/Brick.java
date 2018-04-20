package sprites.Brick;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import help.Info;

public class Brick extends AbstractBrick{

    public Brick(int posX, int posY){
        super(posX, posY, "normalBrick.jpg");
        //il boundBrick si riferiscono al rettangolo invisibile che costruirò intorno alla texture
        //l'altezza e la larghezza sono della texture
    }
}
