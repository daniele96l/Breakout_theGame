package eccezioni;

/**
 * Questa eccezione controlla che il carattere char c passato
 * come parametro del metodo IllegalCharacter sia conforme
 * alle direttive dello sviluppatore.
 *
 * @author: Alberto Schillaci
 * @version 1.0
 */

public class IllegalCharacter extends Exception {
    public IllegalCharacter(char c) {
        super("Il carattere \""+c+"\" non è supportato");
    }
}
