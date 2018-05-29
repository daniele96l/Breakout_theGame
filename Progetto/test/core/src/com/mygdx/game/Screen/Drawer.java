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
     * Questo metodo si occupa di disegnare le texture che servono al menu, per cui disegerà i bottoni per la selezione delle diverse modalità
     * di gioco, per la visualizzazione della classifica o per uscire dal gioco.
     */
    static void drawMainMenu(BreakGame game, Texture menu,Texture playButton,Texture exitButton,Texture multiplayerofflineButton,Texture score,Texture multiplayeronlineButton,int playbutton,int onlinebutton ,int offlinebutton,int scorebutton,int exitbutton)
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
     *
     * @param bricks l'arrey list che contiene i miei mattoncini
     * @param game  la partita corrente
     * @param powerUps l'array list che contiene i miei power up
     * @param numeroPlayer il numero dei giocatori della partita
     * @param paddles l'array list dei player della partita
     * @param palla l'oggetto palla
     *
     * il metodo si occupa di disegnare lo scenario della partita mentre si sta giocando, ad ogni frame
     */
    static void drawMultiplayer(ArrayList<AbstractBrick> bricks, BreakGame game, ArrayList<PowerUp> powerUps, int numeroPlayer, ArrayList<Paddle> paddles, Ball palla)
    {
        game.getBatch().begin();
        for (AbstractBrick brick : bricks) {
            game.getBatch().draw(brick, brick.getPositionBrick().x, brick.getPositionBrick().y, brick.getWidth() * Info.getInstance().getBrickresize(), brick.getHeight() * Info.getInstance().getBrickresize());
        }

        for (PowerUp p : powerUps) {
            game.getBatch().draw(p, p.getBounds().x, p.getBounds().y, p.getWidth() * Info.getInstance().getPowerUpResize(), p.getHeight() * Info.getInstance().getPowerUpResize());
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
     *
     * @param game la partita corrente
     * @param gameOver la texture della schermata di game over
     *
     * il metodo permette di disegnare la schermata di game over quando tutte le vite vengono perse
     */
    static void drawLoseScreen(BreakGame game,Texture gameOver)
    {
        game.getBatch().begin();
        game.getBatch().draw(gameOver, 0, 0);
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            game.setScreen(new MainMenuScreen(game));
        }
        game.getBatch().end();
    }

    /**
     *
     * @param game la partita corrente
     * @param bg la texture che rappresenta lo sfondo che appare durante il gioco
     * @param bricks array list che contiene i mattoncini
     * @param players array list che contiene i giocatori che stanno partecipando alla partita, intesi come Robot o Human
     * @param powerUps array list che contine i power up generati in maniera casuale
     * @param paddles array list che contiene i paddle che partecipano alla partita
     * @param palla
     *
     * il metodo si occupa di disegnare lo scenario della partita ad ogni frame quando si sceglie la modalità di gioco "MultiplayerOffline"
     */
    static void drawMultiplayerOffline(BreakGame game, Texture bg, ArrayList<AbstractBrick> bricks, ArrayList<Player> players,ArrayList<PowerUp> powerUps, ArrayList<Paddle> paddles, Ball palla,int numeroPlayer)
    {
        // togliere il numeroPlayer dalla firma del metodo
        game.getBatch().draw(bg, 0, 0);
        game.getBatch().end();

        Hud hud=new Hud(players, game.getBatch());
        hud.stage.draw();

        game.getBatch().begin();

        for (AbstractBrick brick : bricks) {
            game.getBatch().draw(brick, brick.getPositionBrick().x, brick.getPositionBrick().y, brick.getWidth() * Info.getInstance().getBrickresize(), brick.getHeight() * Info.getInstance().getBrickresize());
            //disegnoimattoncini
        }
        for(PowerUp p:powerUps) {
            game.getBatch().draw(p, p.getBounds().x, p.getBounds().y, p.getWidth()*Info.getInstance().getPowerUpResize(), p.getHeight()*Info.getInstance().getPowerUpResize());
        }
        game.getBatch().draw(paddles.get(0), paddles.get(0).getPosition().x, paddles.get(0).getPosition().y, paddles.get(0).getWidth() * Info.getInstance().getPaddleresizex().get(0), paddles.get(0).getHeight() * Info.getInstance().getPaddleresize());
        game.getBatch().draw(palla, palla.getPositionBall().x, palla.getPositionBall().y, palla.getWidth() * Info.getInstance().getBallresize(), palla.getHeight() * Info.getInstance().getBallresize());

    }

}
