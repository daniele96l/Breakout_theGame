package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import help.Info;
import sprites.Brick;
import sprites.*;

import java.util.ArrayList;

public class Disegnare {


    public void disegnare(SpriteBatch batch, ArrayList<Brick> mattoncini, Paddle Paddle, Ball palla, Texture bg) {

        batch.draw(bg, 0, 0);

        for (Brick Brick : mattoncini) {
            batch.draw(Brick, Brick.getPositionBrick().x, Brick.getPositionBrick().y,Brick.getWidth()* Info.brickresize,Brick.getHeight()* Info.brickresize);
            //disegno i mattoncini
        }


        batch.draw(Paddle, Paddle.getPosition().x, Paddle.getPosition().y, Paddle.getWidth()* Info.paddleresize , Paddle.getHeight()*Info.paddleresize);
        batch.draw(palla, palla.getPositionBall().x, palla.getPositionBall().y,palla.getWidth()* Info.ballresize, palla.getHeight()* Info.ballresize);


    }
}
