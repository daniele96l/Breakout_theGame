package com.mygdx.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.BreakGame;
import help.GameState;


public class WinGameScreen implements Screen {

    private Texture youWin;
    private BreakGame game;
    private OfflineGameScreen oldScreen;
    private GameState gameState;

    public WinGameScreen(BreakGame game, OfflineGameScreen oldScreen,GameState gameState) {
        this.game = game;
        this.oldScreen = oldScreen;
        this.gameState = gameState;
    }

    @Override
    public void show() {
        youWin = new Texture("nextlevel.jpg");
    }

    @Override
    public void render(float delta) {
        game.getBatch().begin();
        game.getBatch().draw(youWin, 0, 0);
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            gameState = GameState.ACTION;
            dispose();
            oldScreen.setGameState(GameState.ACTION);
            game.setScreen(oldScreen);
        }
        game.getBatch().end();
    }

    @Override
    public void resize(int width, int height) {

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
}
