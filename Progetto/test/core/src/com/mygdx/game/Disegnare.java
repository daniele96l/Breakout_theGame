package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import help.Info;
import sprites.Brick;
import sprites.Paddle;

import java.util.ArrayList;

public class Disegnare {


    public void disegnare(SpriteBatch batch, ArrayList<Brick> mattoncini, Paddle Paddle) {

        for (Brick Brick : mattoncini) {
            batch.draw(Brick, Brick.getPositionBrick().x, Brick.getPositionBrick().y,Brick.getWidth()* Info.brickresize,Brick.getHeight()* Info.brickresize);
            //disegno i mattoncini
        }


        batch.draw(Paddle, Paddle.getPosition().x, Paddle.getPosition().y, Paddle.getWidth()* Info.paddleresize , Paddle.getHeight()*Info.paddleresize);



    }
}
