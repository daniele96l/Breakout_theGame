package com.mygdx.game.Screen;
import ClientServer.Server.Client;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.mygdx.game.BreakGame;

public class MultiplayerGameScreen implements Screen {

    private Client client;
    private BreakGame game;

    public MultiplayerGameScreen(BreakGame game) {
        client=new Client();
        this.game=game;
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        game.getBatch().begin();
        ////Disegna dati da server
        game.getBatch().end();

        if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            client.keyPressed("left");
        }
        else {
            if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
                client.keyPressed("right");
            }
            else {
                client.keyPressed("");
            }
        }
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
