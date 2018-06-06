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

import DatabaseManagement.Database;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.mygdx.game.BreakGame;
import com.mygdx.game.Player.Player;
import com.mygdx.game.hud.Hud;
import help.Info;
import sprites.Ball;
import sprites.Brick.AbstractBrick;
import sprites.Brick.Brick;
import sprites.Button;
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
     * Questo metodo si occupa di disegnare le texture che servono al menu, per cui disegerà i bottoni per la selezione delle diverse modalità.
     * di gioco, per la visualizzazione della classifica o per uscire dal gioco.
     *
     * @param game la partita corrente
     *
     */
    public static void drawMainMenu(BreakGame game)
    {
        ArrayList<Button> menuButtons=ButtonCollection.getInstance().getMenuButtons();
        Texture menuTexture=new Texture("menuscreen.jpg");
        game.getBatch().begin();
        game.getBatch().draw(menuTexture,0,0);
        for(Button b:menuButtons) {
            game.getBatch().draw(b, b.getX(), b.getY(), b.getWidth(), b.getHeight());
        }
        game.getBatch().end();
    }

    /**
     * Il metodo si occupa di disegnare lo scenario della partita mentre si sta giocando, ad ogni frame.
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
     * Il metodo permette di disegnare la schermata di game over quando tutte le vite vengono perse.
     *
     * @param game la partita corrente
     *
     */
    public static void drawLoseScreen(BreakGame game) {
        Texture gameOver=new Texture("gameover.jpg");
        game.getBatch().begin();
        game.getBatch().draw(gameOver, 0, 0);
        game.getBatch().end();
    }

    /**
     * Il metodo si occupa di disegnare lo scenario della partita ad ogni frame quando si sceglie la modalità di gioco "MultiplayerOffline".
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
    public static void drawMultiplayerOffline(BreakGame game, Texture bg, ArrayList<Brick> bricks, ArrayList<Player> players, ArrayList<PowerUp> powerUps, ArrayList<Paddle> paddles, Ball palla)  {
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
    public static void drawPauseScreen(BreakGame game) {
        Texture menuScreen=new Texture("menuscreen.jpg");
        ArrayList<Button> pauseButtons=ButtonCollection.getInstance().getPauseButtons();

        game.getBatch().begin();
        game.getBatch().draw(menuScreen, 0, 0);

        for(Button b:pauseButtons) {
            game.getBatch().draw(b, b.getX(), b.getY(), b.getWidth(), b.getHeight());
        }
        game.getBatch().end();
    }
    public static void drawScoreScreen(BreakGame game) {
        Database db=new Database();
        Texture menuScreen=new Texture("menuscreen.jpg");
        ArrayList<Button> scoreButtons=ButtonCollection.getInstance().getScoreButtons();
        BitmapFont bitmapFont = new BitmapFont();
        bitmapFont.setColor(Color.WHITE);
        bitmapFont.getData().setScale(1.6f);
        game.getBatch().begin();
        game.getBatch().draw(menuScreen, 0, 0);
        bitmapFont.draw(game.getBatch(), db.printTable(), 500, 704);
        for(Button b:scoreButtons) {
            game.getBatch().draw(b, b.getX(), b.getY(), b.getWidth(), b.getHeight());
        }
        game.getBatch().end();
    }

}
