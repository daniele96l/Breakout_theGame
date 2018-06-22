package com.mygdx.game.help;

import java.util.ArrayList;
import java.util.Date;

/**
 * La classe viene utilizzata per fare controlli su alcuni intervalli temporali
 *
 * @author ?
 */

public class Timer {
    /**
     *
     * @see Date
     * @param date
     * @param numeroPlayer
     */
    public boolean checkTimer(ArrayList<Date> date, int numeroPlayer){
        if(date != null){
            Date date2 = new Date();
            for(int i =0; i < numeroPlayer;i++)
                if(date2.getTime() - date.get(i).getTime() > Info.getInstance().getDurataPowerUp())
                {
                    Info.getInstance().getPaddleresizex().set(i,Info.getInstance().getPaddleresize());
                    return true;
                }
                return false;
        }
        return false;
    }
}
