package com.mygdx.game;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import sprites.*;



import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class MyGdxGame extends Game {
    private Collision col;
    private SpriteBatch batch;
    private Brick Brick;
    private ArrayList<Brick> mattoncini = new ArrayList();
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
    Music music ;
    private boolean loser;
    BitmapFont bitmapFont ;
    int LostLives =0;
	
	@Override
	public void create () {
        batch = new SpriteBatch();

        bitmapFont = new BitmapFont();
        bitmapFont.setColor(Color.WHITE);
        bitmapFont.getData().setScale(1.2f);
        music =  Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));

        reset();

        start=new Texture("start.jpg");
        gameOver=new Texture("gameover.jpeg");
        youWin=new Texture("nextlevel.jpg");

        gameState=GameState.INIT;
        nextLevel=false;

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		music.setVolume(1);
        music.play();


		if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) { //Barra spaziatrice per iniziare
		    if(gameState.equals(GameState.INIT)) {
		        gameState=GameState.ACTION;
            }
            if(gameState.equals(GameState.YOU_WON)) {
		        reset();
		        nextLevel=false;
		        gameState=GameState.ACTION;
            }
            if(gameState.equals(GameState.GAME_OVER)) {
                reset();
                gameState=GameState.ACTION;
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

        if(gameState.equals(GameState.ACTION) ) {


            palla.getPositionBall().add(palla.getSpeedBall().x * Info.dt, palla.getSpeedBall().y* Info.dt);
            palla.getBoundsBall().setPosition(palla.getPositionBall().x, palla.getPositionBall().y);
            batch.draw(bg, 0, 0);
            batch.draw(palla, palla.getPositionBall().x, palla.getPositionBall().y,palla.getWidth()* Info.ballresize, palla.getHeight()* Info.ballresize);
            bitmapFont.draw(batch, "You lost: "+String.valueOf(LostLives) + " times", 20, 830);
            for (Brick Brick : mattoncini) {
                batch.draw(Brick, Brick.getPositionBrick().x, Brick.getPositionBrick().y,Brick.getWidth()* Info.brickresize,Brick.getHeight()* Info.brickresize);
                //disegno i mattoncini
            }


            batch.draw(Paddle, Paddle.getPosition().x, Paddle.getPosition().y, Paddle.getWidth()* Info.paddleresize , Paddle.getHeight()*Info.paddleresize);

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
                LostLives ++;
                palla.setPositionBall();
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
            if(gameState.equals(GameState.GAME_OVER) ) {
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
        Paddle = new Paddle(0.5f);
        player1 = new CommandPlayer(Paddle);     //istanzio un Commandplayer( posso averne diversi per ogni player
        mattoncini = livello.selectLv(); //la classe livello si occuperà di ritornare l'array list dei mattoncini adatti a questo livello
        bg =livello.getBg();
    }
}
