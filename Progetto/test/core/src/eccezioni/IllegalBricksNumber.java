package eccezioni;

import help.Info;

public class IllegalBricksNumber extends Exception {

    public IllegalBricksNumber(int numX, int numY) {
        super("La griglia del livello deve avere "+ numX+" colonne e meno di "+(numY+1)+" righe");
    }
}
