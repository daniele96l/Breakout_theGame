package com.mygdx.game.graphics.sprites;

import com.mygdx.game.eccezioni.IllegalBrick;
import com.mygdx.game.eccezioni.IllegalPowerUp;
import com.mygdx.game.graphics.sprites.Brick.Brick;
import com.mygdx.game.graphics.sprites.Brick.HardBrick;
import com.mygdx.game.graphics.sprites.Brick.NormalBrick;
import com.mygdx.game.graphics.sprites.powerup.*;

/**
 * La classe viene utilizzata per fare alcuni controlli durante la creazione dello scenario. In particolare permette
 * di controllare che cio che si intende creare sia corretto sintatticamente.
 * Vengono pertanto effettuati check sulla tipologia di powerUp su quella di mattoncini.
 *
 * @author schillaci
 */

public class SpriteFactory {

    private static SpriteFactory instance;

    /**
     * Metodo che controlla e restituisce il tipo di Sprite.
     * Metodo per l'implementazione del pattern Singleton.
     *
     * @return instance il tipo di Sprite in questione
     *
     */
    public static synchronized SpriteFactory getInstance() {
        if (instance == null) {
            instance = new SpriteFactory();
        }
        return instance;
    }

    /**
     * il metodo permette di fare un controllo sulla tipologia di power up nel momento in cui uno di essi viene
     * aggiunto ai mattoncini dello scenario in fase di creazione dello stesso.
     *
     * @param powerUp il power up su cui fare il check
     * @param posX coordinata x
     * @param posY coordinata y
     * le coordinate servono per poter rendere effettivo il power up.
     *
     * @return la tipologia di power up corrispondente a quella passata per parametro, se questa passa il check
     * @throws IllegalPowerUp se non viene passato nessuno dei check, oppure se inizialmente la stringa era vuota.
     */

    public PowerUp getPowerUp(String powerUp, int posX, int posY) throws IllegalPowerUp {
        if(powerUp==null) {
            throw new IllegalPowerUp();
        }
        else if(powerUp.equals("ExtraLife")) {
            return new ExtraLife(posX, posY);
        }
        else if(powerUp.equals("LongPaddle")) {
            return new LongPaddle(posX, posY);
        }
        else if(powerUp.equals("LostLife")) {
            return new LostLife(posX, posY);
        }
        else if(powerUp.equals("ShortPaddle")) {
            return new ShortPaddle(posX, posY);
        }
        else throw new IllegalPowerUp();
    }

    /**
     * i metodo fa un check sulla tipologia di mattoncino che deve essere creato.
     *
     * @param brick una stringa che specifica la tipologia di mattoncino e sulla quale si fa il controllo
     * @param posX la coordinata x in cui verra creato il mattoncino
     * @param posY la coordinata y in cui verra creato il mattoncino
     * @return l'oggetto mattoncino da creare se la stringa passa il check
     * @throws IllegalBrick se la stringa non passa alcun check oppure se era inizialmente vuota.
     */
    public Brick getBrick(String brick, int posX, int posY) throws IllegalBrick {
        if(brick==null) {
            throw new IllegalBrick();
        }
        else if(brick.equals("NormalBrick")) {
            return new NormalBrick(posX, posY);
        }
        else if(brick.equals("HardBrick")) {
            return new HardBrick(posX, posY);
        }
        else throw new IllegalBrick();
    }
}
