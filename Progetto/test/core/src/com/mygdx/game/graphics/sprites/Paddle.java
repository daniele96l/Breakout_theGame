package com.mygdx.game.graphics.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.help.Info;

/**
 * La classe Paddle contiene tutte le informazioni e variabili del paddle
 * per corretto il funzionamento del gioco, quali per esempio la sua texture, il suo
 * suo vettore posizione, la sua velocità, la dimensione e quale giocatore ne è in posseso
 *
 * @author Alberto Schillaci, Daniele Ligato, Cristian Regna
 */


public class Paddle extends Sprite{
    private Vector2 positionM;
    private Rectangle bounds;
    private int giocatore;

    public Paddle(int numeroGiocatori, int giocatore) {
        super(new Texture("mattonalla curva.png"));
        float resize = Info.getInstance().getPaddleresize();
        this.giocatore = giocatore;
        positionM = new Vector2((Info.getInstance().getLarghezza() / numeroGiocatori) * (giocatore - 1) + Info.getInstance().getLarghezza() / (2 * numeroGiocatori) - this.getWidth() / 2 * Info.getInstance().getPaddleresize(), 0);
        bounds = new Rectangle(positionM.x, positionM.y, Paddle.this.getWidth() * Info.getInstance().getPaddleresize(), Paddle.this.getHeight() * Info.getInstance().getPaddleresize());
    }


    /**
     * Questo metodo imposta la dimensione della paddle in base al numero
     * di giocatori che partecipano alla partita
     *
     * @param numeroGiocatori numero di partecipanti alla partita
     */
    public void setDefaultState(int numeroGiocatori) {
        for(int i = 0;i< numeroGiocatori; i++) {
            Info.getInstance().getPaddleresizex().set(i, Info.getInstance().getPaddleresize());
        }
        positionM = new Vector2((Info.getInstance().getLarghezza() / numeroGiocatori) * (giocatore - 1) + Info.getInstance().getLarghezza() / (2 * numeroGiocatori) - this.getWidth() / 2 * Info.getInstance().getPaddleresize(), 0);
        bounds = new Rectangle(positionM.x, positionM.y, Paddle.this.getWidth() * Info.getInstance().getPaddleresize(), Paddle.this.getHeight() * Info.getInstance().getPaddleresize());
    }

    /**
     * Questo metodo restituisce il vettore poszione (x e y) del paddle
     *
     * @return positionM vettore di due dimensioni del paddle
     */
    public Vector2 getPosition() {
        return positionM;
    }

    /**
     * Questo metodo resitituisce i contorni dell'oggetto paddle
     *
     * @return bounds rettangolo che fa da contorno dell'oggetto paddle
     */
    public Rectangle getBounds() {
        bounds = new Rectangle(positionM.x, positionM.y, Paddle.this.getWidth() * Info.getInstance().getPaddleresizex().get(giocatore-1), Paddle.this.getHeight() * Info.getInstance().getPaddleresize());
        return bounds;
    }

    /**
     * Questo metodo restituisce il numero del giocatore
     * proprietario del corrispondente paddle
     *
     * @return giocatore intero che indica l'id del giocatore proprietario del paddle
     */
    public int getGiocatore() {
        return giocatore;
    }
}
