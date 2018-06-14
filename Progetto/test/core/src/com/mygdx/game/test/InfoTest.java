package com.mygdx.game.test;

import com.mygdx.game.help.Info;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
public class InfoTest
{
    @Test
    public void testGetBrickWidth()
    {
        assertEquals(720,Info.getInstance().getBrickWidth());
    }
    @Test
    public void testGetVelPaddle()
    {
        assertEquals(10,Info.getInstance().getVelPaddle());
    }
    @Test
    public void testGetBrickHeight()
    {
        assertEquals(1280,Info.getInstance().getBrickHeight());
    }




}
