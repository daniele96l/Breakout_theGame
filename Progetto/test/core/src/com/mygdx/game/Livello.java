package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import sprites.*;
import java.util.ArrayList;
import com.mygdx.game.*;

public class Livello {



    private int shift = 600;
    private int nColonne;
    private ball ball;
    private int nMatt;
    private Texture bg;
    private mattoncino mattoncino;

    private ArrayList<mattoncino> mattoncini;
    private int lv = 1;

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
        mattoncini=new ArrayList<sprites.mattoncino>();
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

        mattoncini=new ArrayList<sprites.mattoncino>();
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

    public void inceaseLv() {
        this.lv++;
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
