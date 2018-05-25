package eccezioni;

/**
 * @Autor Schillaci
 * Eccezione che viene lanciata se....??
 */

public class IllegalCharacter extends Exception {
    public IllegalCharacter(char c) {
        super("Il carattere \""+c+"\" non è supportato");
    }
}
