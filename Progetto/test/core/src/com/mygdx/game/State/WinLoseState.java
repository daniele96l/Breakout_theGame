package com.mygdx.game.State;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import help.GameState;

public class WinLoseState {

    private SpriteBatch batch;
    private GameState gameState;
    private Texture youWin;
    private Texture gameOver;

    public WinLoseState(SpriteBatch batch, GameState gameState) {
        this.batch = batch;
        this.gameState = gameState;
        youWin = new Texture("nextlevel.jpg");
        gameOver = new Texture("gameover.jpg");
    }

    public GameState draw(){

        if (gameState.equals(GameState.YOU_WON)){
            batch.draw(youWin, 0, 0);
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
                return GameState.ACTION;
            }
            return GameState.YOU_WON;
        }

        if(gameState.equals(GameState.GAME_OVER)){
            batch.draw(gameOver, 0, 0);
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
                return GameState.MENU;
            }
            return GameState.GAME_OVER;
        }


        return gameState;
    }
}
