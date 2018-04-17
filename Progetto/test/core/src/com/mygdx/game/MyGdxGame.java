package com.mygdx.game;
import ClientServer.ChatClient;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Player.HumanPlayer;
import com.mygdx.game.Player.RobotPlayer;
import help.GameState;
import help.Info;
import sprites.*;



import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Input.TextInputListener;
import sprites.Brick.AbstractBrick;

import java.util.ArrayList;

public class MyGdxGame extends Game implements TextInputListener {
    private Collision col;
    private SpriteBatch batch;
    private AbstractBrick AbstractBrick;
    private ArrayList<AbstractBrick> mattoncini = new ArrayList();
    private Ball palla;
    private Paddle paddle1;
    private Paddle paddle2;
    private boolean isMultiplayer;
    private Texture bg;
    private Texture start;
    private Texture gameOver;
    private Texture menu;
    private Texture youWin;
    private GameState gameState;
    private CommandPlayer commandPlayer1;
    private CommandPlayer commandPlayer2;
    private HumanPlayer player1;
    private RobotPlayer robotPlayer;
    private Texture multiplayerButton;
    private Texture resumeButton;
    private Texture playButton;
    private Texture exitButton;
    private Texture menuButton;
    private  Livello livello = new Livello(AbstractBrick, palla);
    private boolean nextLevel;
    private ArrayList<Integer> indici;
    private ArrayList<AbstractBrick> mattoncini1;
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
        playButton = new Texture("play.png");
        exitButton = new Texture("exit.png");
        menu = new Texture("menuscreen.jpg");
        menuButton = new Texture("menu.png");
        resumeButton = new Texture("resume.png");
        multiplayerButton = new Texture("multiplayer.png");



        gameState=GameState.MENU;
        nextLevel=false;
        isMultiplayer=false;
       // Gdx.input.getTextInput(textInputListener, "Title", "Default text", "OK");
       // Gdx.app.log("Text", "test");


    }

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		music.setVolume(1);
        music.play();

        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
        {
            if(gameState.equals(GameState.GAME_OVER))
                Gdx.app.exit();
        }
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


        if(gameState == GameState.PAUSE){
            batch.draw(menu,0,0);
            batch.draw(resumeButton, Info.larghezza/2 - playButton.getWidth()/2, 550);      // al posto di metterli cosi posso usare delle costanti
            batch.draw(exitButton, Info.larghezza/2 - exitButton.getWidth()/2,150);
            batch.draw(menuButton, Info.larghezza/2 - multiplayerButton.getWidth()/2, 350);
            System.out.println(Gdx.input.getY() + " " + Gdx.input.getX() );

            if(Gdx.input.getX()>Info.larghezza/2 - playButton.getWidth()/2    && (Gdx.input.getX() <Info.larghezza/2 + exitButton.getWidth()/2)  && (Info.altezza - Gdx.input.getY() > 150 && (Info.altezza - Gdx.input.getY() < 150 + exitButton.getHeight() ))){
                if(Gdx.input.isTouched())
                    Gdx.app.exit();
            }
            if(Gdx.input.getX()>Info.larghezza/2 - resumeButton.getWidth()/2    && (Gdx.input.getX() <Info.larghezza/2 + resumeButton.getWidth()/2)  && (Info.altezza - Gdx.input.getY() > 550 && (Info.altezza - Gdx.input.getY() < 550 + resumeButton.getHeight() ))){
                if(Gdx.input.isTouched())
                    gameState = GameState.ACTION;
            }
            if(Gdx.input.getX()>Info.larghezza/2 - menuButton.getWidth()/2    && (Gdx.input.getX() <Info.larghezza/2 + menuButton.getWidth()/2)  && (Info.altezza - Gdx.input.getY() > 350 && (Info.altezza - Gdx.input.getY() < 350 + menuButton.getHeight() ))){
                if(Gdx.input.isTouched()) {
                    gameState = GameState.MENU;
                    reset();

                }
            }
        }

        if(gameState == GameState.MENU){
            Gdx.gl.glClearColor(1, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            batch.draw(menu,0,0);
            batch.draw(playButton, Info.larghezza/2 - playButton.getWidth()/2, 550);      // al posto di metterli cosi posso usare delle costanti
            batch.draw(exitButton, Info.larghezza/2 - exitButton.getWidth()/2,150);
            batch.draw(multiplayerButton, Info.larghezza/2 - multiplayerButton.getWidth()/2, 350);//immagini bruttissime
            if(Gdx.input.getX()>Info.larghezza/2 - playButton.getWidth()/2    && (Gdx.input.getX() <Info.larghezza/2 + exitButton.getWidth()/2)  && (Info.altezza - Gdx.input.getY() > 150 && (Info.altezza - Gdx.input.getY() < 150 + exitButton.getHeight() ))){
                if(Gdx.input.isTouched())
                    Gdx.app.exit();
            }
            if(Gdx.input.getX()>Info.larghezza/2 - playButton.getWidth()/2    && (Gdx.input.getX() <Info.larghezza/2 + playButton.getWidth()/2)  && (Info.altezza - Gdx.input.getY() > 550 && (Info.altezza - Gdx.input.getY() < 550 + exitButton.getHeight() ))){
                if(Gdx.input.isTouched())
                    gameState = GameState.ACTION;
            }
            if(Gdx.input.getX()>Info.larghezza/2 - multiplayerButton.getWidth()/2    && (Gdx.input.getX() <Info.larghezza/2 + multiplayerButton.getWidth()/2)  && (Info.altezza - Gdx.input.getY() > 350 && (Info.altezza - Gdx.input.getY() < 350 + multiplayerButton.getHeight() ))){
                if(Gdx.input.isTouched())
                    gameState = GameState.MULTIPLAYER;
            }
        }

        if(gameState == GameState.MULTIPLAYER){
            isMultiplayer=true;
            paddle1=new Paddle(0.5f,2,1);
            paddle2=new Paddle(0.5f, 2, 2);
            robotPlayer=new RobotPlayer(palla, paddle2);
            commandPlayer1=new CommandPlayer(paddle1, player1, 2, 1);
            commandPlayer2=new CommandPlayer(paddle2, robotPlayer, 2, 2);
            gameState=GameState.ACTION;
        }

        if(gameState.equals(GameState.ACTION) ) {

            palla.getPositionBall().add(palla.getSpeedBall().x * Info.dt, palla.getSpeedBall().y* Info.dt);
            palla.getBoundsBall().setPosition(palla.getPositionBall().x, palla.getPositionBall().y);
            batch.draw(bg, 0, 0);

            for (AbstractBrick AbstractBrick : mattoncini) {
                batch.draw(AbstractBrick, AbstractBrick.getPositionBrick().x, AbstractBrick.getPositionBrick().y,AbstractBrick.getWidth()* Info.brickresize,AbstractBrick.getHeight()* Info.brickresize);
                //disegno i mattoncini
            }


            batch.draw(paddle1, paddle1.getPosition().x, paddle1.getPosition().y, paddle1.getWidth()* Info.paddleresize , paddle1.getHeight()*Info.paddleresize);
            batch.draw(palla, palla.getPositionBall().x, palla.getPositionBall().y,palla.getWidth()* Info.ballresize, palla.getHeight()* Info.ballresize);


            bitmapFont.draw(batch, "You lost: "+String.valueOf(LostLives) + " times", 20, 830);

            commandPlayer1.move();     //mi permette di muovere il giocatore
            if(commandPlayer1.checkpause()){
                gameState = GameState.PAUSE;
            }

            if(isMultiplayer) {
                batch.draw(paddle2, paddle2.getPosition().x, paddle2.getPosition().y, paddle2.getWidth()* Info.paddleresize , paddle2.getHeight()*Info.paddleresize);
                commandPlayer2.move();
            }

            gestisciCollisioni();

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
                livello.setnMatMorbidi(0);
                livello.setnMat(0);
                matEliminati = 0;
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
        paddle1 = new Paddle(0.5f,1, 1);
        player1=new HumanPlayer();
        commandPlayer1 = new CommandPlayer(paddle1, player1, 1, 1);     //istanzio un Commandplayer( posso averne diversi per ogni player
        if(isMultiplayer) {
            paddle1=new Paddle(0.5f,2,1);
            paddle2=new Paddle(0.5f, 2, 2);
            robotPlayer=new RobotPlayer(palla, paddle2);
            commandPlayer1=new CommandPlayer(paddle1, player1, 2, 1);
            commandPlayer2=new CommandPlayer(paddle2, robotPlayer,2,2);
        }
        mattoncini = livello.selectLv(); //la classe livello si occuperà di ritornare l'array list dei mattoncini adatti a questo livello
        bg =livello.getBg();
    }

    public void gestisciCollisioni() {
        float oldSpeedBallX=palla.getSpeedBall().x;
        float oldSpeedBallY=palla.getSpeedBall().y;

        indici=new ArrayList<Integer>();
        for (AbstractBrick Abstractbrick : mattoncini) {
            //dato che ho un arraylist devo aggiornare le condizioni dei mattoncini dentro un ciclo for
            col = new Collision(Abstractbrick, palla);
            if (col.check()) {
                indici.add(mattoncini.indexOf(Abstractbrick));
            }
        }

        col.checkBorderCollision();

        col.checkside(paddle1);
        if(isMultiplayer) {
            col.checkside(paddle2);
        }

        if (!indici.isEmpty()) {
            if(indici.size()>=2) {
                ArrayList<AbstractBrick> tempMatt=new ArrayList<sprites.Brick.AbstractBrick>();
                palla.setSpeedBall(new Vector2(oldSpeedBallX,-oldSpeedBallY));
                for(int i:indici) {
                    if(mattoncini.get(indici.get(0)).getDurezza() == 0) {//i mattoncini vengono eliminati solo se sono quelli MORBIDI
                        tempMatt.add(mattoncini.get(i));
                        matEliminati ++;
                    }
                }
                for(AbstractBrick Abstractbrick:tempMatt) {
                    mattoncini.remove(Abstractbrick);
                }
            }
            else {
                if(mattoncini.get(indici.get(0)).getDurezza() == 0) { //se i mattoncini sono mattoncini "morbidi" li posso eliminare
                    mattoncini.remove((int) indici.get(0));
                    matEliminati++;
                }
            }
        }
    }

    @Override
    public void input(String text) {

    }

    @Override
    public void canceled() {

    }
}
