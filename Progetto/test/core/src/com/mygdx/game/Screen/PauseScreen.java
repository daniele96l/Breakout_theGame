package com.mygdx.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Scaling;
import com.mygdx.game.BreakGame;
import help.GameState;
import help.Info;

public class PauseScreen implements Screen {

    private BreakGame game;
    private GameState gameState;
    private Texture menu;
    private Texture resumeButton;
    private Texture exitButton;
    private Texture menuButton;
    private OfflineGameScreen oldScreen;
    private float newHeight, newWight, coeffDimensionale = 1;


    public PauseScreen(BreakGame game, OfflineGameScreen oldScreen) {
        this.game = game;
        this.oldScreen=oldScreen;
    }

    @Override
    public void show() {
        menu = new Texture("menuscreen.jpg");
        menuButton = new Texture("menu.png");
        resumeButton = new Texture("resume.png");
        exitButton = new Texture("exit.png");
        resumeButton = new Texture("resume.png");

    }

    @Override
    public void render(float delta) {
        game.getBatch().begin();



        game.getBatch().draw(menu, 0, 0);
        game.getBatch().draw(resumeButton, Info.larghezza / 2 - resumeButton.getWidth() / 2, 550);//alpostodimetterlicosipossousaredellecostanti
        game.getBatch().draw(exitButton, Info.larghezza / 2 - exitButton.getWidth() / 2, 150);
        game.getBatch().draw(menuButton, Info.larghezza / 2 - menuButton.getWidth() / 2, 350);


        if (Gdx.input.getX() > Info.larghezza*coeffDimensionale / 2 - exitButton.getWidth()*coeffDimensionale / 2 && (Gdx.input.getX() < Info.larghezza*coeffDimensionale / 2 + exitButton.getWidth()*coeffDimensionale / 2) && (Info.altezza*coeffDimensionale - Gdx.input.getY() > 150*coeffDimensionale && (Info.altezza *coeffDimensionale- Gdx.input.getY() < 150 *coeffDimensionale+ exitButton.getHeight()*coeffDimensionale))) {
            if (Gdx.input.justTouched())
                Gdx.app.exit();
        }

        if (Gdx.input.getX()*coeffDimensionale > Info.larghezza *coeffDimensionale/ 2 - menuButton.getWidth()*coeffDimensionale / 2 && (Gdx.input.getX() < Info.larghezza*coeffDimensionale / 2 + menuButton.getWidth()*coeffDimensionale / 2) && (Info.altezza*coeffDimensionale - Gdx.input.getY() > 550*coeffDimensionale && (Info.altezza*coeffDimensionale - Gdx.input.getY() < 550 *coeffDimensionale+ menuButton.getHeight()*coeffDimensionale))) {
            if (Gdx.input.justTouched()) {
                game.setScreen(oldScreen);
            }
        }

        if (Gdx.input.getX()*coeffDimensionale > Info.larghezza *coeffDimensionale/ 2 - menuButton.getWidth()*coeffDimensionale / 2 && (Gdx.input.getX() < Info.larghezza*coeffDimensionale / 2 + menuButton.getWidth()*coeffDimensionale / 2) && (Info.altezza*coeffDimensionale - Gdx.input.getY() > 350*coeffDimensionale && (Info.altezza*coeffDimensionale - Gdx.input.getY() < 350 *coeffDimensionale+ menuButton.getHeight()*coeffDimensionale))) {
            if (Gdx.input.justTouched()) {
                game.setScreen(new MainMenuScreen(game));
            }
        }





                game.getBatch().end();

    }

    @Override
    public void resize(int width, int height) {

        this.newHeight = height;
        this.newWight = width;

        coeffDimensionale = newHeight/(float)Info.altezza;


        Vector2 size = Scaling.fit.apply(800, 850, width, height);
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
}
