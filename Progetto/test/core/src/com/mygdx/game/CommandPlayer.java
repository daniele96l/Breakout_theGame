package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import help.Info;
import sprites.Paddle;

public class CommandPlayer {

    private Paddle paddle;

    public CommandPlayer(Paddle paddle){
        this.paddle = paddle
        ;
    }


    // richiamo questo metodo nel MainGDfx cosi posso far muovere il personaggio
    public void Move()
    {
         if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            if(paddle.getPosition().x > 0) { //controllo il range in cui la Paddle si può muovere
                paddle.getPosition().add(-10, 0);
                paddle.getBounds().setPosition(paddle.getPosition().x, paddle.getPosition().y);
            }
            //if(paddle.getPosition().x < )
        }
            if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            if(paddle.getPosition().x < 800 - paddle.getWidth()* Info.paddleresize ) {
                paddle.getPosition().add(10, 0);//controllo il range in cui la Paddle si può muovere
                paddle.getBounds().setPosition(paddle.getPosition().x, paddle.getPosition().y);
            }
        }
    }

    public boolean checkpause(){
        if(Gdx.input.isKeyPressed(Input.Keys.P)){
            return true;
        }
        return false;
    }
}
