/*
* DA FARE:
*
* non ha senso passare tutti quei parametri, essendo la classe fatta per questo scopo,
* si deve supporre che i bottoni, le texture e le schermate siano creati all'interno di questa
* pattern creator
*
* Oppure, se i parametri servono anche nella classe di provenienza, creare una classe intermedia
* che abbia questi parametri e con il pattern information expert fornirli ad entrambe le classi
* (Ad esempio, al posto di creare i bottoni in mainmenuscreen, li creo nell'information expert, e li passo a drawer
* e a mainmenuscreen)
*
*
*
*
*
*
*
*
* */


package com.mygdx.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.BreakGame;
import com.mygdx.game.Player.Player;
import com.mygdx.game.hud.Hud;
import help.Info;
import sprites.Ball;
import sprites.Brick.AbstractBrick;
import sprites.Brick.Brick;
import sprites.Paddle;
import sprites.powerup.AbstractPowerUp;
import sprites.powerup.PowerUp;

import java.util.ArrayList;

/**
 * @author Cotogni, Ligato
 *
 * Questa classe si occupa solo di disegnare alcuni elementi necessari per il gioco, sia nel main menù, che nel gameplay
 */

public class Drawer
{
    /**
     *Questo metodo si occupa di disegnare le texture che servono al menu, per cui disegerà i bottoni per la selezione delle diverse modalità
     * di gioco, per la visualizzazione della classifica o per uscire dal gioco.
     *
     * @param game la partita corrente
     * @param menu la texture sfondo
     * @param playButton la texture che rappresenta il bottone "play"
     * @param exitButton la texture che rappresenta il bottone "exit"
     * @param multiplayerofflineButton la texture che rappresenta il bottone "multiplayer offline"
     * @param score la texture che rappresenta il bottone "score"
     * @param multiplayeronlineButton  la texture che rappresenta il bottone "multiplayer online"
     * @param playbutton  l'altezza del bottone play
     * @param onlinebutton l'altezza del bottone "multiplayer online"
     * @param offlinebutton l'altezza del bottone "multiplayer offline"
     * @param scorebutton l'altezza del bottone "score"
     * @param exitbutton  l'altezza del bottone "exit"
     *
     */
    public static void drawMainMenu(BreakGame game, Texture menu,Texture playButton,Texture exitButton,Texture multiplayerofflineButton,Texture score,Texture multiplayeronlineButton,int playbutton,int onlinebutton ,int offlinebutton,int scorebutton,int exitbutton)
    {
        game.getBatch().begin();
        game.getBatch().draw(menu, 0, 0);
        game.getBatch().draw(playButton, Info.getInstance().getLarghezza() / 2 - playButton.getWidth() / 2, playbutton);//alpostodimetterlicosipossousaredellecostanti
        game.getBatch().draw(exitButton, Info.getInstance().getLarghezza() / 2 - exitButton.getWidth() / 2, exitbutton);
        game.getBatch().draw(multiplayeronlineButton, Info.getInstance().getLarghezza() / 2 - multiplayerofflineButton.getWidth() / 2, offlinebutton);//immaginibruttissime
        game.getBatch().draw(score,Info.getInstance().getLarghezza() / 2 - score.getWidth() / 2, scorebutton );
        game.getBatch().draw(multiplayerofflineButton,Info.getInstance().getLarghezza() / 2 - multiplayeronlineButton.getWidth() / 2, onlinebutton );
    }

    /**
     * il metodo si occupa di disegnare lo scenario della partita mentre si sta giocando, ad ogni frame
     *
     * @param bricks l'arrey list che contiene i miei mattoncini
     * @param game  la partita corrente
     * @param powerUps l'array list che contiene i miei power up
     * @param numeroPlayer il numero dei giocatori della partita
     * @param paddles l'array list dei player della partita
     * @param palla l'oggetto palla
     *
     */
    public static void drawMultiplayer(ArrayList<Brick> bricks, BreakGame game, ArrayList<PowerUp> powerUps, int numeroPlayer, ArrayList<Paddle> paddles, Ball palla)
    {
        game.getBatch().begin();
        for (Brick b : bricks) {
            AbstractBrick brick=(AbstractBrick) b;
            game.getBatch().draw(brick, brick.getPositionBrick().x, brick.getPositionBrick().y, brick.getWidth() * Info.getInstance().getBrickresize(), brick.getHeight() * Info.getInstance().getBrickresize());
        }

        for (PowerUp p : powerUps) {
            AbstractPowerUp powerUp=(AbstractPowerUp)p;
            game.getBatch().draw(powerUp, powerUp.getBounds().x, powerUp.getBounds().y, powerUp.getWidth() * Info.getInstance().getPowerUpResize(), powerUp.getHeight() * Info.getInstance().getPowerUpResize());
        }
        if (numeroPlayer > 0) {
            for (int i = 0; i < numeroPlayer; i++) {
                game.getBatch().draw(paddles.get(i), paddles.get(i).getPosition().x, paddles.get(i).getPosition().y, paddles.get(i).getWidth() * Info.getInstance().getPaddleresizex().get(i), paddles.get(i).getHeight() * Info.getInstance().getPaddleresize());
            }
        }
        game.getBatch().draw(palla, palla.getPositionBall().x, palla.getPositionBall().y, palla.getWidth() * Info.getInstance().getBallresize(), palla.getHeight() * Info.getInstance().getBallresize());
        game.getBatch().end();
    }

    /**
     * il metodo permette di disegnare la schermata di game over quando tutte le vite vengono perse
     *
     * @param game la partita corrente
     * @param gameOver la texture della schermata di game over
     *
     */
    public static void drawLoseScreen(BreakGame game,Texture gameOver)
    {
        game.getBatch().begin();
        game.getBatch().draw(gameOver, 0, 0);
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            game.setScreen(new MainMenuScreen(game));
        }
        game.getBatch().end();
    }

    /**
     * il metodo si occupa di disegnare lo scenario della partita ad ogni frame quando si sceglie la modalità di gioco "MultiplayerOffline"
     *
     * @param game la partita corrente
     * @param bg la texture che rappresenta lo sfondo che appare durante il gioco
     * @param bricks array list che contiene i mattoncini
     * @param players array list che contiene i giocatori che stanno partecipando alla partita, intesi come Robot o Human
     * @param powerUps array list che contine i power up generati in maniera casuale
     * @param paddles array list che contiene i paddle che partecipano alla partita
     * @param palla
     *
     */
    public static void drawMultiplayerOffline(BreakGame game, Texture bg, ArrayList<Brick> bricks, ArrayList<Player> players, ArrayList<PowerUp> powerUps, ArrayList<Paddle> paddles, Ball palla, int numeroPlayer)
    {
        // togliere il numeroPlayer dalla firma del metodo
        game.getBatch().draw(bg, 0, 0);
        game.getBatch().end();

        Hud hud=new Hud(players, game.getBatch());
        hud.stage.draw();

        game.getBatch().begin();

        for (Brick b : bricks) {
            AbstractBrick brick=(AbstractBrick)b;
            game.getBatch().draw(brick, brick.getPositionBrick().x, brick.getPositionBrick().y, brick.getWidth() * Info.getInstance().getBrickresize(), brick.getHeight() * Info.getInstance().getBrickresize());
            //disegnoimattoncini
        }
        for(PowerUp p:powerUps) {
            AbstractPowerUp powerUp=(AbstractPowerUp)p;
            game.getBatch().draw(powerUp, powerUp.getBounds().x, powerUp.getBounds().y, powerUp.getWidth() * Info.getInstance().getPowerUpResize(), powerUp.getHeight() * Info.getInstance().getPowerUpResize());        }
        game.getBatch().draw(paddles.get(0), paddles.get(0).getPosition().x, paddles.get(0).getPosition().y, paddles.get(0).getWidth() * Info.getInstance().getPaddleresizex().get(0), paddles.get(0).getHeight() * Info.getInstance().getPaddleresize());
        game.getBatch().draw(palla, palla.getPositionBall().x, palla.getPositionBall().y, palla.getWidth() * Info.getInstance().getBallresize(), palla.getHeight() * Info.getInstance().getBallresize());

    }
    public static void drawPauseScreen(BreakGame game,Texture menu,Texture resumeButton,Texture exitButton, Texture menuButton)
    {
        game.getBatch().draw(menu, 0, 0);
        game.getBatch().draw(resumeButton, Info.getInstance().getLarghezza() / 2 - resumeButton.getWidth() / 2, 550);//alpostodimetterlicosipossousaredellecostanti
        game.getBatch().draw(exitButton, Info.getInstance().getLarghezza() / 2 - exitButton.getWidth() / 2, 150);
        game.getBatch().draw(menuButton, Info.getInstance().getLarghezza() / 2 - menuButton.getWidth() / 2, 350);
    }
    public static void drawScoreScreen(BreakGame game,Texture scoreScreen,Texture backButton,int backbuttonx,int backbuttony)
    {
        game.getBatch().draw(scoreScreen, 0, 0);
        game.getBatch().draw(backButton, backbuttonx, backbuttony);
    }

}
