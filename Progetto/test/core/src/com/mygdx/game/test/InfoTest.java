package com.mygdx.game.test;

import com.mygdx.game.help.Info;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
public class InfoTest
{
    Info info = new Info();
    @Test
    public void testGetBrickWidth()
    {
        assertEquals(720,info.getAltezza());
    }
    @Test
    public void testGetVelPaddle()
    {
        assertEquals(10,info.getVelPaddle());
    }
    @Test
    public void testGetBrickHeight()
    {
        assertEquals(1280,info.getLarghezza());
    }




}
