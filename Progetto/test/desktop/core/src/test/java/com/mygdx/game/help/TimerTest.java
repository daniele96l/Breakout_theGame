package com.mygdx.game.help;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *La classe permette il testing della classe Timer attraverso l'uso del framework Junit
 *
 * @author Cotogni
 *
 */
public class TimerTest {
    @Test
    public void checkTimerTestTrue()
    {
        ArrayList<Date> date = new ArrayList<Date>();
        Date date1 =  new Date();
        date1.setTime(date1.getTime()-(2*Info.getInstance().getDurataPowerUp()));
        date.add(date1);
        Info.getInstance().getPaddleresizex().add(0.5f);
        assertEquals(true,new Timer().checkTimer(date,1));
    }
    @Test
    public void checkTimerTestFalse()
    {
        ArrayList<Date> date = new ArrayList<Date>();
        Date date1 =  new Date();
        date1.setTime(date1.getTime()+ Info.getInstance().getDurataPowerUp());
        date.add(date1);
        assertEquals(false,new Timer().checkTimer(date,1));
    }


}