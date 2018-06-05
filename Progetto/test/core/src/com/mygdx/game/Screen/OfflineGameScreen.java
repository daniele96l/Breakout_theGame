package com.mygdx.game.Screen;

import DatabaseManagement.Database;
import DatabaseManagement.Enum.DropType;
import DatabaseManagement.Enum.TableType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Scaling;
import com.mygdx.game.BreakGame;
import com.mygdx.game.Collision;
import com.mygdx.game.CommandPlayer;
import com.mygdx.game.GameManager.GameManager;
import com.mygdx.game.GameManager.OfflineGameManager;
import com.mygdx.game.Leaderboard.Score;
import com.mygdx.game.Levels.GestoreLivelli;
import com.mygdx.game.Player.HumanPlayer;
import com.mygdx.game.Player.Player;
import com.mygdx.game.Player.RobotPlayer;
import com.mygdx.game.hud.Hud;
import help.GameState;
import help.*;
import sprites.Ball;
import sprites.Brick.Brick;
import sprites.Paddle;
import sprites.powerup.PowerUp;

import java.util.ArrayList;
import java.util.Date;

/**
 * @author Ligato, Schillaci, Regna
 *
 * Questa classe gestisce la logica della partita quando si sta giocando offline
 */
public class OfflineGameScreen implements Screen {

    private OfflineGameManager gameManager;


    public OfflineGameScreen(BreakGame game, int numeroPlayer) {
        gameManager=new OfflineGameManager(game, this, numeroPlayer);
    }

    @Override
    public void show() {

    }

    /**
     * Il metodo render aggiorna la schermata ogni frame e renderizza a schermo gli oggetti grafici:
     * Prende i mattoncini e li renderizza, renderizza il background, seleziona la musica adatta allo stato e ne fa il play
     * imposta la posizione della palla, gestisce l'arraylist dei powerUp (aggiungendoli o rimuovendoli), aggiorna la HUD
     * e controlla lo stato corrente del gioco.
     *
     * @param delta è l'itervallo di temp che intercorre tra una chiamata e l'altra di questo metodo.
     */

    @Override
    public void render(float delta) {
        gameManager.render();
    }

    /**
     * Si occupa di ridimensionare la finestra
     *
     * @param width
     * @param height
     */
    @Override
    public void resize(int width, int height) {

        Vector2 size = Scaling.fit.apply(Info.getInstance().getLarghezza(), Info.getInstance().getAltezza(), width, height);
        int viewportX = (int)(width - size.x) / 2;
        int viewportY = (int)(height - size.y) / 2;
        int viewportWidth = (int)size.x;
        int viewportHeight = (int)size.y;
        Gdx.gl.glViewport(viewportX, viewportY, viewportWidth, viewportHeight);

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    public static void setPlayerName(String playerName) {
        OfflineGameManager.setPlayerName(playerName);
    }

    public static String getPlayerName() {
        return OfflineGameManager.getPlayerName();
    }

    public void setGameState(GameState gameState) {
        gameManager.setGameState(gameState);
    }
}

