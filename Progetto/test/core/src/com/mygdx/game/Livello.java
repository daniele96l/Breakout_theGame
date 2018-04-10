package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import sprites.*;
import java.util.ArrayList;

public class Livello {



    private int shift = 600;
    private int shiftAltezza = 700;
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

        mattoncini = drawLine(1);
        return  mattoncini;

    }


    public ArrayList<Brick> creaLv2(){


        mattoncini = drawLine(2);
        return  mattoncini;

    }


    public ArrayList<Brick> drawLine(int nRighe){

        mattoncini=new ArrayList<Brick>();
        int nColonne = 4;


        for(int y = 0; y < nRighe; y++ ){

            for ( int i = 0; i < nColonne; i++) { //con un ciclo creo tutti i mattoncini e li metto dentro un arraylist
                Brick = new Brick(shift, shiftAltezza - y*100);
                mattoncini.add(Brick);
                shift -= 150; //ogni Brick è distante dall'altro di uno shift

            }
            shift = 600;

        }

        nMatt = nColonne * nRighe;
        shift = 600;

        return mattoncini;
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
