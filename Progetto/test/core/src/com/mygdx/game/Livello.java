package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import sprites.*;
import java.util.ArrayList;

public class Livello {

    private int shift = 700;
    private int shiftAltezza = 700;
    private int nColonne;
    private Ball Ball;
    private int nMatt;
    private Texture bg;
    private Brick Brick;
    private int moreLayer;
    private int coeffy = 100;
    private int coeffx = 130;

    private ArrayList<Brick> mattoncini;
    private ArrayList<Brick> mattoncini1;
    private int lv = 1;
    private Texture texture;

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
        if(lv ==3)
        {
            bg = new Texture("bgLv3.jpg");
            return creaLv3();
        }

        if(lv ==4)
        {
            bg = new Texture("bgLv4.jpg");
            coeffy = 30;
            coeffx = 160;
            Info.setBrickresize(0.3f);
            return creaLv4();
        }
        return creaLvVuoto();

    }

    public ArrayList<Brick> creaLvVuoto(){
        return  mattoncini;
    }

    public ArrayList<Brick> creaLv1(){
        return  drawLine(6,1,  1);
    }

    public ArrayList<Brick> creaLv2(){
        return drawLine(5, 2,  0 );
    }

    public ArrayList<Brick> creaLv3(){
        return drawLine(7, 3, 0 );
    }

    public ArrayList<Brick> creaLv4(){
        return drawLine(7, 3, 0 );
    }




    public ArrayList<Brick> drawLine(int nColonne, int nRighe, int moreLines){

        mattoncini=new ArrayList<Brick>();

        for(int y = 0; y < nRighe; y++ ){
            for ( int i = 0; i < nColonne; i++) { //con un ciclo creo tutti i mattoncini e li metto dentro un arraylist
                Brick = new Brick(shift, shiftAltezza - y*coeffy, "normalBrick.jpg", 0);
                mattoncini.add(Brick);
                shift -= coeffx * Info.brickresize; //ogni Brick è distante dall'altro di uno shift

            }
            shift = 700;
        }

       /* for(int j = 0; j < moreLines; j++ ){
            for ( int i = 0; i < nColonne; i++) { //con un ciclo creo tutti i mattoncini e li metto dentro un arraylist
                Brick = new Brick(shift, shiftAltezza - j*coeffy, "brick.jpg", 1);
                mattoncini.add(Brick);
                shift -= coeffx * Info.brickresize; //ogni Brick è distante dall'altro di uno shift

            }
            shift = 700;
        } */

        nMatt = nColonne * nRighe;
        return mattoncini;
    }

    public ArrayList<Brick> aggiuntivi(int contatorelivelli){


        float distanza = coeffx* Info.ballresize;
        mattoncini1=new ArrayList<Brick>();

        if(contatorelivelli == 3 )
            for(int y = 0; y < 1; y++ ){
                for ( int i = 0; i < 3; i++) { //con un ciclo creo tutti i mattoncini e li metto dentro un arraylist
                    Brick = new Brick(shift - 2*(int)distanza, shiftAltezza - 1*coeffy, "brick.jpg", 0);
                    mattoncini1.add(Brick);
                    shift -= coeffx * Info.brickresize; //ogni Brick è distante dall'altro di uno shift
                }
                shift = 700;
            }
        return  mattoncini1;
    }


    public void inceaseLv() {
        this.lv++;
    }

    public int getShift() {
        return shift;
    }

    public int getLv() {
        return lv;
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
