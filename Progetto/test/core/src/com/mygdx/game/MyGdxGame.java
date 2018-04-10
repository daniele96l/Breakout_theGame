package com.mygdx.game;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import sprites.*;



import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import javax.tools.Tool;
import java.awt.*;
import java.util.ArrayList;

public class MyGdxGame extends Game {
    private Collision col;
    private SpriteBatch batch;
    private Brick Brick;
    private ArrayList<Brick> mattoncini = new ArrayList<Brick>();
    private Ball palla;
    private Paddle Paddle;
    private Texture bg;
    private Texture start;
    private Texture gameOver;
    private Texture youWin;
    private GameState gameState;
    private CommandPlayer player1;
    private  Livello livello = new Livello(Brick, palla);
    private boolean nextLevel;

	
	@Override
	public void create () {
        batch = new SpriteBatch();
        reset();
        System.out.println("eseguo");

        start=new Texture("start.jpg");
        gameOver=new Texture("gameover.jpeg");
        youWin=new Texture("nextlevel.jpg");

        /*Immagini ovviamente temporanee (fanno cagare)*/
        gameState=GameState.INIT;
        nextLevel=false;
        
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        palla.getPositionBall().add(palla.getSpeedBall().x * Info.dt, palla.getSpeedBall().y* Info.dt);
        palla.getBoundsBall().setPosition(palla.getPositionBall().x, palla.getPositionBall().y);
		if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) { //Barra spaziatrice per iniziare
		    if(gameState.equals(GameState.INIT)) {
		        gameState=GameState.ACTION;
            }
            if(gameState.equals(GameState.YOU_WON)) {
		        reset();
		        nextLevel=false;
		        gameState=GameState.INIT;
            }
            if(gameState.equals(GameState.GAME_OVER)) {
                reset();
                gameState=GameState.INIT;
            }
        }

        if(nextLevel) {//deve stare dentro render perchè deve essere controllato sempre
            mattoncini = livello.selectLv();  //ritorno l'array adatto al nuovo livello
            bg = livello.getBg(); //reimposto il bg

        }

        drawScene();
    }

    public void drawScene() {
        int index = -1;
        batch.begin();


        if(gameState.equals(GameState.ACTION)) {
            batch.draw(bg, 0, 0);
            batch.draw(palla, palla.getPositionBall().x, palla.getPositionBall().y);

            for (Brick Brick : mattoncini) {
                batch.draw(Brick, Brick.getPositionBrick().x, Brick.getPositionBrick().y);

                //disegno i mattoncini
            }

            batch.draw(Paddle, Paddle.getPosition().x, Paddle.getPosition().y);

            player1.Move();     //mi permette di muovere il giocatore

            for (Brick Brick : mattoncini) {
                //dato che ho un arraylist devo aggiornare le condizioni dei mattoncini dentro un ciclo for
                col = new Collision(Brick, palla);
                if (col.check()) {
                    index = mattoncini.indexOf(Brick);
                }
            }

            col.checkside(Paddle);
            //palla.update((float) 2, Paddle, Brick);
            if (index != -1) {
                mattoncini.remove(mattoncini.get(index));
            }
            if(mattoncini.isEmpty()) {
                gameState=GameState.YOU_WON;
                livello.inceaseLv();
            }
            if(palla.getPositionBall().y<=0) {
                gameState=GameState.GAME_OVER;
            }
        }
        else {
            if(gameState.equals(GameState.INIT)) {
                batch.draw(start,0,0);
            }
            if(gameState.equals(GameState.YOU_WON)) {
                nextLevel=true;
                batch.draw(youWin,0,0);
            }
            if(gameState.equals(GameState.GAME_OVER)) {
                batch.draw(gameOver,0,0);
            }
        }
        batch.end();
    }
	
	@Override
	public void dispose () {
		batch.dispose();
	}

	public void reset() {
        palla = new Ball();
        Paddle = new Paddle();
        player1 = new CommandPlayer(Paddle);     //istanzio un Commandplayer( posso averne diversi per ogni player
        mattoncini = livello.selectLv(); //la classe livello si occuperà di ritornare l'array list dei mattoncini adatti a questo livello
        bg =livello.getBg();
    }
}
