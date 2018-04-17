package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import help.Info;
import sprites.*;
import sprites.Brick.AbstractBrick;
import sprites.Brick.Brick;
import sprites.Brick.HardBrick;

import java.util.ArrayList;

public class Livello {

    private int shift = 700;
    private int shiftAltezza = 700;
    private sprites.Brick.AbstractBrick AbstractBrick;
    private Ball Ball;
    private Texture bg;
    private sprites.Brick.Brick Brick;
    private sprites.Brick.HardBrick HardBrick;
    private int coeffy = 100;
    private int coeffx = 130;
    int nMat = 0;
    int nMatMorbidi= 0;
    private ArrayList<AbstractBrick> mattoncini;
    private int lv = 1;


    public Livello (AbstractBrick AbstractBrick, Ball Ball){
        this.AbstractBrick = AbstractBrick;
        this.Ball = Ball;
    }

    public  ArrayList<AbstractBrick> selectLv(){
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

    public ArrayList<AbstractBrick> creaLvVuoto(){
        return  mattoncini;
    }

    public ArrayList<AbstractBrick> creaLv1(){
        return  drawLine(6,1,  0,0);
    }

    public ArrayList<AbstractBrick> creaLv2(){
        return drawLine(5, 2,  0 ,0);
    }

    public ArrayList<AbstractBrick> creaLv3(){
        return drawLine(7, 3, 8 , 12);
    }

    public ArrayList<AbstractBrick> creaLv4(){
        return drawLine(14, 10, 100 , 110);
    }


    public int getnMat() {
        return nMat;
    }


    public void setnMat(int nMat) {
        this.nMat = nMat;
    }

    public void setnMatMorbidi(int nMatMorbidi) {
        this.nMatMorbidi = nMatMorbidi;
    }

    public ArrayList<AbstractBrick> drawLine(int nColonne, int nRighe, int StartHard, int FinishHard){

        mattoncini=new ArrayList<AbstractBrick>();

        for(int y = 0; y < nRighe; y++ ){
            for ( int i = 0; i < nColonne; i++) { //con un ciclo creo tutti i mattoncini e li metto dentro un arraylist
                if((nMat > StartHard ) && nMat < FinishHard) {
                    HardBrick = new HardBrick(shift, shiftAltezza - y * coeffy, "brick.jpg");
                    mattoncini.add(HardBrick);
                }
                else {
                    Brick = new Brick(shift, shiftAltezza - y * coeffy, "normalBrick.jpg");
                    nMatMorbidi++;
                    mattoncini.add(Brick);
                }


                shift -= coeffx * Info.brickresize; //ogni Brick è distante dall'altro di uno shift
                nMat++;
            }
            shift = 700;
        }

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

    public ArrayList<AbstractBrick> getMattoncini() {
        return mattoncini;
    }
}
