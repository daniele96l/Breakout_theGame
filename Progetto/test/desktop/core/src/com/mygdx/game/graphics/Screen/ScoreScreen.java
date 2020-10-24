package com.mygdx.game.graphics.Screen;

import com.badlogic.gdx.Screen;
import com.mygdx.game.BreakGame;
import com.mygdx.game.graphics.Drawer;
import com.mygdx.game.logic.InputTouch;
import com.mygdx.game.logic.Resizer;

/**
 * La classe gestisce la schermata della classifica
 *
 * @author Regna, Schillaci, Ligato
 */

public class ScoreScreen implements Screen {
//Applicato HighCoesion

    private float newHeight, newWidth, coeffDimensionale = 1;
    private float barreNere = 0;
    private float tempVet[];
    private BreakGame game;
    private Resizer resizer;

    public ScoreScreen(BreakGame game) {
        this.game = game;
        tempVet = new float[2];
        resizer = new Resizer();
    }

    /**
     * Si occupa di salvare nei parametri i valori telle texture, Gli array list e impostare i Bitmap
     */

    @Override
    public void show() {
    }

    /**
     * Si occupa di renderizzare la finestra, quindi mostrate tutte le texture, e controllare se il bottone sia cliccato
     *
     * @param delta frequenza di aggiornamento frame
     */

    @Override
    public void render(float delta) {
        Drawer.drawScoreScreen(game);

        InputTouch.checkInputScoreScreen(newWidth, coeffDimensionale, game, newHeight, barreNere);
    }


    /**
     * Si occupa di ridimensionare la finestra
     *
     * @param width larghezza finestra
     * @param height altezza finestra
     */
    @Override
    public void resize(int width, int height) {
        this.newHeight = height;
        this.newWidth = width;

        tempVet = resizer.toResize(height, width);
        barreNere = tempVet[0];
        coeffDimensionale = tempVet[1];
    }
    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {}

}
