package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import sprites.*;
import java.util.ArrayList;

public class Livello {



    private int shift = 600;
    private int nColonne;
    private Ball Ball;
    private int nMatt;
    private Texture bg;
    private Brick Brick;

    private ArrayList<Brick> mattoncini;
    private int lv = 1;

    public Livello (Brick Brick, Ball Ball){
        this.Brick = Brick;
        this.Ball = Ball;
    }

    public  ArrayList<Brick> selectLv(){
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

    public ArrayList<Brick> creaLvVuoto(){
        return  mattoncini;

    }

    public ArrayList<Brick> creaLv1(){
        mattoncini=new ArrayList<Brick>();
        int nColonne = 4;
        int nRighe = 1;
        for(int i = 0; i< nColonne; i++){ //con un ciclo creo tutti i mattoncini e li metto dentro un arraylist
            Brick = new Brick(shift , 700  );
            mattoncini.add(Brick);
            shift -= 150; //ogni Brick è distante dall'altro di uno shift
        }
        nMatt = nColonne * nRighe;
        shift = 600;

        return  mattoncini;

    }

    public ArrayList<Brick> creaLv2(){

        mattoncini=new ArrayList<Brick>();
        int nColonne = 5;
        int nRighe = 1;

        for(int i = 0; i< nColonne; i++){ //con un ciclo creo tutti i mattoncini e li metto dentro un arraylist
            Brick = new Brick(shift , 700  );
            mattoncini.add(Brick);
            shift -= 150; //ogni Brick è distante dall'altro di uno shift
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

    public Brick getBrick() {
        return Brick;
    }

    public ArrayList<Brick> getMattoncini() {
        return mattoncini;
    }
}
