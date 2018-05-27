package com.mygdx.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.BreakGame;
import help.Info;
import sprites.Ball;
import sprites.Brick.AbstractBrick;
import sprites.Brick.Brick;
import sprites.Paddle;
import sprites.powerup.PowerUp;

import java.util.ArrayList;

/**
 * @Author Cotogni, Ligato
 *
 * Questa classe si occupa solo di disegnare alcuni elementi necessari per il gioco, sia nel main menù, che nel gameplay
 */

public class Drawer
{
    /**
     *
     * @param game  ??????M
     * @param menu la texture sfondo
     * @param playButton la texture che rappresenta il bottone "play"
     * @param exitButton la texture che rappresenta il bottone "exit"
     * @param multiplayerofflineButton la texture che rappresenta il bottone "mul..."
     * @param score la texture che rappresenta il bottone "score"
     * @param multiplayeronlineButton  la texture che rappresenta il bottone "mul.."
     * @param playbutton  l'altezza del bottone play
     * @param onlinebutton l'altezza del bottone ...
     * @param offlinebutton l'altezza del bottone ...
     * @param scorebutton l'altezza del bottone e...
     * @param exitbutton  l'altezza del bottone exit
     *
     * Questo metodo si occupa di disegnare le texture che servono al menu
     */
    static void drawMainMenu(BreakGame game, Texture menu,Texture playButton,Texture exitButton,Texture multiplayerofflineButton,Texture score,Texture multiplayeronlineButton,int playbutton,int onlinebutton ,int offlinebutton,int scorebutton,int exitbutton)
    {
        game.getBatch().begin();
        game.getBatch().draw(menu, 0, 0);
        game.getBatch().draw(playButton, Info.larghezza / 2 - playButton.getWidth() / 2, playbutton);//alpostodimetterlicosipossousaredellecostanti
        game.getBatch().draw(exitButton, Info.larghezza / 2 - exitButton.getWidth() / 2, exitbutton);
        game.getBatch().draw(multiplayeronlineButton, Info.larghezza / 2 - multiplayerofflineButton.getWidth() / 2, offlinebutton);//immaginibruttissime
        game.getBatch().draw(score,Info.larghezza / 2 - score.getWidth() / 2, scorebutton );
        game.getBatch().draw(multiplayerofflineButton,Info.larghezza / 2 - multiplayeronlineButton.getWidth() / 2, onlinebutton );
    }

    /**
     *
     * @param bricks l'arrey list che contiene i miei mattoncini
     * @param game  ???
     * @param powerUps l'array list che contiene i miei power up
     * @param numeroPlayer il numero dei giocatori della partita
     * @param paddles l'array list dei player della partita
     * @param palla l'oggetto palla
     *
     * Questa classe si occupa di disegnare gli oggetti mentre si gioce
     *
     */
    static void drawMultiplayer(ArrayList<AbstractBrick> bricks, BreakGame game, ArrayList<PowerUp> powerUps, int numeroPlayer, ArrayList<Paddle> paddles, Ball palla)
    {
        game.getBatch().begin();
        for (AbstractBrick brick : bricks) {
            game.getBatch().draw(brick, brick.getPositionBrick().x, brick.getPositionBrick().y, brick.getWidth() * Info.brickresize, brick.getHeight() * Info.brickresize);
        }

        for (PowerUp p : powerUps) {
            game.getBatch().draw(p, p.getBounds().x, p.getBounds().y, p.getWidth() * Info.powerUpResize, p.getHeight() * Info.powerUpResize);
        }
        if (numeroPlayer > 0) {
            for (int i = 0; i < numeroPlayer; i++) {
                game.getBatch().draw(paddles.get(i), paddles.get(i).getPosition().x, paddles.get(i).getPosition().y, paddles.get(i).getWidth() * Info.paddleresizex.get(i), paddles.get(i).getHeight() * Info.paddleresize);
            }
        }
        game.getBatch().draw(palla, palla.getPositionBall().x, palla.getPositionBall().y, palla.getWidth() * Info.ballresize, palla.getHeight() * Info.ballresize);
        game.getBatch().end();
    }
}
