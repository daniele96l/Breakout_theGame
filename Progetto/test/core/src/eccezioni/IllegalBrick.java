package eccezioni;

/**
 * Eccezione di tipologia di mattoncino non valida
 *
 * @author ?
 */
public class IllegalBrick extends Exception{
    public IllegalBrick() {
        super("Brick non valido");
    }

}
