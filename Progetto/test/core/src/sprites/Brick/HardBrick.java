package sprites.Brick;

/**
 * Questa classe estende la classe astratta più generale Brick
 * e definisce che è il mattoncino "indistruttibile", assegnandogli
 * una posizione (x e y) e una immagine all'interno del gioco
 *
 * @author ligato
 */
public class HardBrick extends AbstractBrick {

    public HardBrick(int posX, int posY){
        super(posX, posY, "brick.jpg");
        deletable=false;
        //il boundBrick si riferiscono al rettangolo invisibile che costruirò intorno alla texture
        //l'altezza e la larghezza sono della texture
    }


}
