package com.mygdx.game;
import com.badlogic.gdx.graphics.Texture;
import sprites.*;



import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MyGdxGame extends Game {
    int nColonne = 4;
    private Collision col;
    SpriteBatch batch;
    public mattoncino mattoncino;
    public ArrayList<mattoncino> mattoncini = new ArrayList<mattoncino>();
    public  ball palla;
    public mattonella mattonella;
    public Texture bg;
    public  int shift = 600;
    Livello livello = new Livello();
    int nDeleted = 0;
    int nLv = 1;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		palla = new ball();
		mattonella  = new mattonella();

        mattoncini = livello.selectLv(nLv);

        bg = livello.getBg();

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(bg, 0,0);
        batch.draw(palla, palla.getPositionBall().x, palla.getPositionBall().y);

        for(mattoncino mattoncino: mattoncini){
            batch.draw(mattoncino, mattoncino.getPositionBrick().x, mattoncino.getPositionBrick().y);
            //disegno i mattoncini
        }
		batch.draw(mattonella, mattonella.getPosition().x,mattonella.getPosition().y);
		mattonella.update(1);

        int index = -1;

        for(mattoncino mattoncino: mattoncini){
            //dato che ho un arraylist devo aggiornare le condizioni dei mattoncini dentro un ciclo for
            col = new Collision(mattoncino,palla);
            if(col.check()){
                index = mattoncini.indexOf(mattoncino);
            }
        }
        palla.update((float) 2, mattonella, mattoncino);

        if(index != -1) {
            mattoncini.remove(mattoncini.get(index));
            nDeleted ++;
        }
        if(nDeleted == 3){
            nLv = 2;
            livello.selectLv(nLv);
        }
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
