package com.mygdx.game.help;

import java.util.ArrayList;
import java.util.Date;

/**
 * La classe viene utilizzata per fare controlli su alcuni intervalli temporali
 *
 * @author Regna, Schillaci
 */

public class Timer {
    /**
     * Il metodo controlla che l'intervallo di tempo tra i tempi contenuti in date e l'istante in cui
     * viene invocato il metodo stesso, ecceda o meno la durata dei powerUp.
     *
     * @param date è un arraylist di date, cioè di orari espressi in millisecondi
     * @param numeroPlayer è il numero totale di giocatori partecipanti alla partita in quel momento
     *
     * @see Date
     * @see Info
     */
    public void checkTimer(ArrayList<Date> date, int numeroPlayer){
        if(date != null){
            Date date2 = new Date();
            for(int i =0; i < numeroPlayer;i++)
                if(date2.getTime() - date.get(i).getTime() > Info.getInstance().getDurataPowerUp())
                    Info.getInstance().getPaddleresizex().set(i,Info.getInstance().getPaddleresize());
        }
    }
}
