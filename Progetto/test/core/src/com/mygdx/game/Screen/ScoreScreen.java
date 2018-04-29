package com.mygdx.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

        if (Gdx.input.getX() > Info.larghezza / 2 - backButton.getWidth() / 2 && (Gdx.input.getX() < Info.larghezza / 2 + backButton.getWidth() / 2) && (Info.altezza - Gdx.input.getY() > 50 && (Info.altezza - Gdx.input.getY() < 50 + backButton.getHeight()))) {
            if (Gdx.input.justTouched())
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
