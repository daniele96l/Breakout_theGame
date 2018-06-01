package eccezioni;

public class IllegalPowerUp extends Exception {
    public IllegalPowerUp() {
        super("Power up non valido");
    }
}
