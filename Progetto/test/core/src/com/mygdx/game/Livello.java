package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import sprites.*;
import java.util.ArrayList;
import com.mygdx.game.*;


public class Livello {



    int shift = 600;
    int nColonne;
    ball ball;
    int nMatt;
    public Texture bg;
    public mattoncino mattoncino;
    public  Collision coll = new Collision(mattoncino, ball);

    public ArrayList<mattoncino> mattoncini = new ArrayList<mattoncino>();
    int lv = 1;

    public Livello (mattoncino mattoncino, ball ball){
        this.mattoncino = mattoncino;
        this.ball = ball;
    }

    public  ArrayList<mattoncino> selectLv(){
        if(lv == 1){
            bg = new Texture("bg.jpg");
            return creaLv1();
        }

        if(lv ==2) {
            bg = new Texture("bgLv2.jpg");
            return creaLv2();
        }

        return creaLvVuoto();
    }

    public ArrayList<mattoncino> creaLvVuoto(){
        return  mattoncini;

    }

    public ArrayList<mattoncino> creaLv1(){
        int nColonne = 4;
        int nRighe = 1;
        for(int i = 0; i< nColonne; i++){ //con un ciclo creo tutti i mattoncini e li metto dentro un arraylist
            mattoncino = new mattoncino(shift , 700  );
            mattoncini.add(mattoncino);
            shift -= 150; //ogni mattoncino è distante dall'altro di uno shift
        }
        nMatt = nColonne * nRighe;
        shift = 600;

        return  mattoncini;

    }

    public ArrayList<mattoncino> creaLv2(){

        int nColonne = 5;
        int nRighe = 1;

        for(int i = 0; i< nColonne; i++){ //con un ciclo creo tutti i mattoncini e li metto dentro un arraylist
            mattoncino = new mattoncino(shift , 700  );
            mattoncini.add(mattoncino);
            shift -= 150; //ogni mattoncino è distante dall'altro di uno shift
        }
        shift = 600;
        nMatt = nColonne * nRighe;


        return  mattoncini;

    }
    public boolean nextLevel (){
        if ( coll.getEliminati() == 1 ){ //se i mattoncini eliminati sono uguali ai mattoncini del mio livello incremento il mio livello
            lv ++;
            System.out.println("Prossimo livello");
            coll.setEliminati(0);
            ball.reposition();

           return true;
        }

        return false;

    }




    public int getShift() {
        return shift;
    }

    public Texture getBg() {
        return bg;
    }

    public sprites.mattoncino getMattoncino() {
        return mattoncino;
    }

    public ArrayList<sprites.mattoncino> getMattoncini() {
        return mattoncini;
    }
}
