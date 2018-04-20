package com.mygdx.game.Leaderboard;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;

import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import help.GameState;
import help.Info;

import java.util.ArrayList;
import java.util.Collections;
import java.util.*;


public class Leaderboard implements Comparable<Score> {

    private SpriteBatch batch;
    private Texture menu, backButton;
    BitmapFont bitmapFont;
    private ArrayList<Score> scores;
    private Score score;
    private GameState gameState;
    private Texture scoreScreen;


    public Leaderboard (SpriteBatch batch, GameState gameState){
        this.batch = batch;
        this.gameState = gameState;
        bitmapFont = new BitmapFont();
        bitmapFont.setColor(Color.WHITE);
        bitmapFont.getData().setScale(1.2f);
        scores = new ArrayList<Score>();
        scoreScreen = new Texture("scoreFinal.png");

        draw();

    }

    public GameState draw(){
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        backButton = new Texture("menu.png");

        batch.draw(scoreScreen, 0, 0);
        batch.draw(backButton, 270, 50);

        getFromDB();
        //theBest10();
        bestScores();
        if (Gdx.input.getX() > Info.larghezza / 2 - backButton.getWidth() / 2 && (Gdx.input.getX() < Info.larghezza / 2 + backButton.getWidth() / 2) && (Info.altezza - Gdx.input.getY() > 50 && (Info.altezza - Gdx.input.getY() < 50 + backButton.getHeight()))) {
            if (Gdx.input.isTouched())
                return GameState.MENU;
        }

         return GameState.SCORE;

    }


    public void bestScores(){
///////////////////////////////////best 10
        for(int i = 0; i< 10; i++){
            bitmapFont.draw(batch, scores.get(i).name + " "  + scores.get(i).point, 350, 700 - 50*i);

        }

    }

    public void getFromDB(){
        //improvviso

        for(int i = 0; i< 10; i++){
            score = new Score("Player" + "point", i);
            scores.add(score);


        }








    Collections.sort(scores, new Comparator<Score>() {
        @Override
        public int compare(Score o1, Score o2) {
             if(o1.point < o2.point)
                 return 1;
            if(o1.point > o2.point)
                return -1;
            return 0;
        }
    });




    }



    @Override
    public int compareTo(Score o) {
        return 0;
    }
}
