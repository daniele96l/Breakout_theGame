package com.mygdx.game;
import ClientServer.ChatClient;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import sprites.*;



import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Input.TextInputListener;

import java.util.ArrayList;

public class MyGdxGame extends Game implements TextInputListener {
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
    private ArrayList<Integer> indici;
    private ArrayList<Brick> mattoncini1;
    private  int matEliminati;
    Music music ;
    Music music2 ;
    private boolean loser;
    BitmapFont bitmapFont ;
    int LostLives =0;
    String text;
    TextInputListener textInputListener;
	ChatClient chatClient = new ChatClient() ;
	@Override
	public void create () {
        batch = new SpriteBatch();

        bitmapFont = new BitmapFont();
        bitmapFont.setColor(Color.WHITE);
        bitmapFont.getData().setScale(1.2f);
        music =  Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        music2 =  Gdx.audio.newMusic(Gdx.files.internal("Untitled.mp3"));


        reset();

        start=new Texture("start.jpg");
        gameOver=new Texture("gameover.jpeg");
        youWin=new Texture("nextlevel.jpg");

        gameState=GameState.INIT;
        nextLevel=false;
       // Gdx.input.getTextInput(textInputListener, "Title", "Default text", "OK");
       // Gdx.app.log("Text", "test");


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
		        music2.pause();
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
      //  chatClient.start_main(palla.getPositionBall());
        if(nextLevel) {//deve stare dentro render perchè deve essere controllato sempre
            mattoncini = livello.selectLv();  //ritorno l'array adatto al nuovo livello
            bg = livello.getBg(); //reimposto il bg

        }

        drawScene();

    }

    public void drawScene() {
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

            float oldSpeedBallX=palla.getSpeedBall().x;
            float oldSpeedBallY=palla.getSpeedBall().y;

            indici=new ArrayList<Integer>();
            for (Brick brick : mattoncini) {
                //dato che ho un arraylist devo aggiornare le condizioni dei mattoncini dentro un ciclo for
                col = new Collision(brick, palla);
                if (col.check()) {
                    indici.add(mattoncini.indexOf(brick));
                }
            }

            col.checkside(Paddle);

            if (!indici.isEmpty()) {
                if(indici.size()==2) {
                    palla.setSpeedBall(new Vector2(oldSpeedBallX,oldSpeedBallY));
                    mattoncini.remove(Math.min(indici.get(0), indici.get(1)));
                    mattoncini.remove(Math.min(indici.get(0), indici.get(1)));
                }
                else {
                    if(mattoncini.get((int)indici.get(0)).getDurezza() == 0) { //se i mattoncini sono mattoncini "morbidi" li posso eliminare
                        mattoncini.remove((int) indici.get(0));
                        matEliminati++;
                    }
                }
            }
            System.out.println(matEliminati+ " = " + livello.nMatMorbidi);
            if(matEliminati == livello.nMatMorbidi) {
                gameState=GameState.YOU_WON;
                livello.inceaseLv();
            }

            if(palla.getPositionBall().y<=0) {
                gameState=GameState.GAME_OVER;
                livello.setnMatMorbidi(0);
                livello.setnMat(0);
                matEliminati = 0;
                LostLives ++;
                music2.play();
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
                music.pause();
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

    @Override
    public void input(String text) {

    }

    @Override
    public void canceled() {

    }
}
