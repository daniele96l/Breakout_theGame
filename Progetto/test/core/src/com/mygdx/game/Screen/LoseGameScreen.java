package com.mygdx.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.BreakGame;

public class LoseGameScreen implements Screen {

    private Texture gameOver;
    private BreakGame game;

    public LoseGameScreen(BreakGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        gameOver = new Texture("gameover.jpg");
    }

    @Override
    public void render(float delta) {
        game.getBatch().begin();
        game.getBatch().draw(gameOver, 0, 0);
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            game.setScreen(new MainMenuScreen(game));
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
