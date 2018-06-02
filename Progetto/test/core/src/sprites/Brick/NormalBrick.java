package sprites.Brick;

/**
 * @author Daniele Ligato
 * Questa classe estende la classe astratta più generale Brick
 * e definisce quello che è il mattoncino "classico", assegnandogli
 * una posizione (x e y) e una immagine all'interno del gioco
 */

public class NormalBrick extends AbstractBrick {

    public NormalBrick(int posX, int posY){
        super(posX, posY, "normalBrick.jpg");
        deletable=true;
        //il boundBrick si riferiscono al rettangolo invisibile che costruirò intorno alla texture
        //l'altezza e la larghezza sono della texture
    }
}
