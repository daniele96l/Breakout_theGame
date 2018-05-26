package sprites.Brick;

/**
 * @author Daniele Ligato
 * Questa classe estende la classe astratta più generale AbstractBrick
 * e definisce quello che è il mattoncino "classico", assegnandogli
 * una posizione (x e y) e una immagine all'interno del gioco
 */

public class Brick extends AbstractBrick{

    public Brick(int posX, int posY){
        super(posX, posY, "normalBrick.jpg");
        //il boundBrick si riferiscono al rettangolo invisibile che costruirò intorno alla texture
        //l'altezza e la larghezza sono della texture
    }
}
