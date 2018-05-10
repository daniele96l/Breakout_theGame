package com.mygdx.game.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.BreakGame;
import com.mygdx.game.ClientServer.Client;

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
        game.getBatch().draw(new Texture("bg.jpg"), 0, 0);
        ////Disegna dati da server
        game.getBatch().end();

        if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            client.keyPressed(Input.Keys.LEFT);
        }
        else {
            if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
                client.keyPressed(Input.Keys.RIGHT);
            }
            else {
                client.keyPressed(0);
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
