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
    private int durezza = 0;

    private ArrayList<Brick> mattoncini;
    private ArrayList<Brick> mattoncini1;
    private int lv = 3;
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
        return  drawLine(6,1,  0,0,0,0, 1);
    }

    public ArrayList<Brick> creaLv2(){
        return drawLine(5, 2,  0 ,0,0,0, 0);
    }

    public ArrayList<Brick> creaLv3(){
        return drawLine(7, 3, 1 , 1,3,2, 1);
    }

    public ArrayList<Brick> creaLv4(){
        return drawLine(7, 3, 0 , 0,0,0,0);
    }




    public ArrayList<Brick> drawLine(int nColonne, int nRighe, int HardLines, int HardLineFrom, int HardCol, int HardColFrom , int durezza){

        mattoncini=new ArrayList<Brick>();

        for(int y = 0; y < nRighe; y++ ){
            for ( int i = 0; i < nColonne; i++) { //con un ciclo creo tutti i mattoncini e li metto dentro un arraylist
                Brick = new Brick(shift, shiftAltezza - y*coeffy, "normalBrick.jpg", 0);
                mattoncini.add(Brick);
                shift -= coeffx * Info.brickresize; //ogni Brick è distante dall'altro di uno shift

            }
            shift = 700;
        }

        float distanzaX = coeffx* Info.brickresize;



        shiftAltezza -= coeffy*HardLineFrom;
        shift -= coeffx*Info.brickresize*HardColFrom;

        for(int j = 0; j < HardLines; j++ ){
            for ( int z = 0; z < HardCol; z++) { //con un ciclo creo tutti i mattoncini e li metto dentro un arraylist
                Brick = new Brick(shift , shiftAltezza , "brick.jpg", durezza);
                mattoncini.add(Brick);
                shift -= coeffx * Info.brickresize; //ogni Brick è distante dall'altro di uno shift

            }
            shift = 700;
        }

        nMatt = nColonne * nRighe;
        return mattoncini;
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
