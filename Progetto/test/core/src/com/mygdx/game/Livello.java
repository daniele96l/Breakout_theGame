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
        if(lv ==3)
        {
            bg = new Texture("bgLv2.jpg");
            return creaLv3();
        }
        return creaLvVuoto();

    }

    public ArrayList<Brick> creaLvVuoto(){
        return  mattoncini;
    }

    public ArrayList<Brick> creaLv1(){
        return  drawLine(6,1);
    }

    public ArrayList<Brick> creaLv2(){
        return drawLine(5, 2);
    }

    public ArrayList<Brick> creaLv3(){
        return drawLine(7, 3);
    }

    public ArrayList<Brick> drawLine(int nColonne, int nRighe){

        mattoncini=new ArrayList<Brick>();

        for(int y = 0; y < nRighe; y++ ){
            for ( int i = 0; i < nColonne; i++) { //con un ciclo creo tutti i mattoncini e li metto dentro un arraylist
                Brick = new Brick(shift, shiftAltezza - y*100);
                mattoncini.add(Brick);
                shift -= 130 * Info.brickresize; //ogni Brick è distante dall'altro di uno shift
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
