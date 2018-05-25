package sprites.Brick;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import help.Info;

/**
 * @author ligato
 * Classe che rappresenta il mattoncino 'indistruttibile'
 */
public class HardBrick extends AbstractBrick {

    public HardBrick(int posX, int posY){
        super(posX, posY, "brick.jpg");
        //il boundBrick si riferiscono al rettangolo invisibile che costruirò intorno alla texture
        //l'altezza e la larghezza sono della texture
    }
}
