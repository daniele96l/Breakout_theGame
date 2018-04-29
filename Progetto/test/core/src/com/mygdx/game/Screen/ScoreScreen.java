package com.mygdx.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Scaling;
import com.mygdx.game.BreakGame;
import com.mygdx.game.Leaderboard.Score;
import help.GameState;
import help.Info;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ScoreScreen implements Screen {

    private Texture menu, backButton;
    BitmapFont bitmapFont;
    private ArrayList<Score> scores;
    private Score score;
    private GameState gameState;
    private Texture scoreScreen;
    private float newHeight, newWight, coeffDimensionale = 1;

    private BreakGame game;

    public ScoreScreen(BreakGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        bitmapFont = new BitmapFont();
        bitmapFont.setColor(Color.WHITE);
        bitmapFont.getData().setScale(1.2f);
        scores = new ArrayList<Score>();
        scoreScreen = new Texture("scoreFinal.png");

    }

    @Override
    public void render(float delta) {
        game.getBatch().begin();
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        backButton = new Texture("menu.png");

        game.getBatch().draw(scoreScreen, 0, 0);
        game.getBatch().draw(backButton, 270, 50);

        getFromDB();
        //theBest10();
        bestScores(game.getBatch());

        if (Gdx.input.getX() > Info.larghezza*coeffDimensionale / 2 - backButton.getWidth()*coeffDimensionale / 2 && (Gdx.input.getX() < Info.larghezza*coeffDimensionale / 2 + backButton.getWidth()*coeffDimensionale / 2) && (Info.altezza*coeffDimensionale - Gdx.input.getY() > 50*coeffDimensionale && (Info.altezza*coeffDimensionale - Gdx.input.getY() < 50*coeffDimensionale + backButton.getHeight()*coeffDimensionale))) {
            if (Gdx.input.justTouched())
                game.setScreen(new MainMenuScreen(game));
        }
        game.getBatch().end();
    }

    @Override
    public void resize(int width, int height) {

        this.newHeight = height;
        this.newWight = width;

        coeffDimensionale = newHeight/(float) Info.altezza;


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

    public void bestScores(SpriteBatch batch) {
///////////////////////////////////best 10
        for (int i = 0; i < 10; i++) {
            bitmapFont.draw(batch, scores.get(i).getName() + " " + scores.get(i).getPoint(), 350, 700 - 50 * i);

        }

    }

    public void getFromDB() {
        //improvviso

        for (int i = 0; i < 10; i++) {
            score = new Score("Player" + "point", i);
            scores.add(score);


        }


        Collections.sort(scores, new Comparator<Score>() {
            @Override
            public int compare(Score o1, Score o2) {
                if (o1.getPoint() < o2.getPoint())
                    return 1;
                if (o1.getPoint() > o2.getPoint())
                    return -1;
                return 0;
            }
        });


    }
}
