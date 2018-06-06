package com.mygdx.game.graphics.Screen;

import com.badlogic.gdx.Screen;
import com.mygdx.game.BreakGame;
import com.mygdx.game.logic.GameManager.OfflineGameManager;
import com.mygdx.game.logic.Resizer;
import com.mygdx.game.help.GameState;

/**
 * @author Ligato, Schillaci, Regna
 *
 * Questa classe gestisce la logica della partita quando si sta giocando offline
 */
public class OfflineGameScreen implements Screen {

    private OfflineGameManager gameManager;
    private Resizer resizer;


    public OfflineGameScreen(BreakGame game, int numeroPlayer) {
        gameManager=new OfflineGameManager(game, this, numeroPlayer);
    }

    @Override
    public void show() {
        resizer = new Resizer();
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

        resizer.toResize(width,height);

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

