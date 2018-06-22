package com.mygdx.game.test;

import java.util.ArrayList;
import java.util.Date;

import com.mygdx.game.help.Info;
import com.mygdx.game.help.Timer;
import org.junit.Test;
import  org.junit.Assert.*;

import static org.junit.Assert.assertEquals;

public class TestTimer
{
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
