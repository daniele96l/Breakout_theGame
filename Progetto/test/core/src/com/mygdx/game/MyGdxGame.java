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
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		palla = new ball();
		mattonella  = new mattonella();

        for(int i = 0; i< nColonne; i++){ //con un ciclo creo tutti i mattoncini e li metto dentro un arraylist
            mattoncino = new mattoncino(shift , 700  );
            mattoncini.add(mattoncino);
            shift -= 150; //ogni mattoncino è distante dall'altro di uno shift
        }
        bg = new Texture("bg.jpg");
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

            //ovviamente anche l'aggiornamento del
        }
        palla.update((float) 2, mattonella, mattoncino);
        if(index != -1) {
            mattoncini.remove(mattoncini.get(index));
        }
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
