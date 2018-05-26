package eccezioni;

/**
 * @author: Alberto Schillaci
 * @version 1.0
 * Questa eccezione controlla che il carattere char c passato
 * come parametro del metodo IllegalCharacter sia conforme
 * alle direttive dello sviluppatore
 */

public class IllegalCharacter extends Exception {
    public IllegalCharacter(char c) {
        super("Il carattere \""+c+"\" non è supportato");
    }
}
