package eccezioni;

import help.Info;

/**
 * @Autor Schillaci
 * Questa ecccezione viene lanciata se si imposta un numero di mattoncini non compatibili alla dimensione della finestra
 */

public class IllegalBricksNumber extends Exception {

    public IllegalBricksNumber(int numX, int numY) {
        super("La griglia del livello deve avere "+ numX+" colonne e meno di "+(numY+1)+" righe");
    }
}
