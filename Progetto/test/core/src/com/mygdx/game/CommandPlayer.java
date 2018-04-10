package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import sprites.Paddle;

public class CommandPlayer {

    private Paddle mat;

    public CommandPlayer(Paddle mat){
        this.mat = mat;
    }


    // richiamo questo metodo nel MainGDfx cosi posso far muovere il personaggio
    public void Move()
    {
         if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            if(mat.getPosition().x > 0) { //controllo il range in cui la Paddle si può muovere
                mat.getPosition().add(-10, 0);
                mat.getBounds().setPosition(mat.getPosition().x, mat.getPosition().y);
            }
        }
            if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            if(mat.getPosition().x < 800 - mat.getWidth()) {
                mat.getPosition().add(10, 0);//controllo il range in cui la Paddle si può muovere
                mat.getBounds().setPosition(mat.getPosition().x, mat.getPosition().y);
            }
        }
    }
}
