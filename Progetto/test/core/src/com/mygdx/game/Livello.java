package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import help.Info;
import sprites.*;
import java.util.ArrayList;

public class Livello {

    private int shift = 700;
    private int shiftAltezza = 700;
    private int nColonne;
    private Ball Ball;
    private Texture bg;
    private Brick Brick;
    private int coeffy = 100;
    private int coeffx = 130;
    int nMat = 0;
    int nMatMorbidi= 0;


    private ArrayList<Brick> mattoncini;
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
        return  drawLine(6,1,  0,0);
    }

    public ArrayList<Brick> creaLv2(){
        return drawLine(5, 2,  0 ,0);
    }

    public ArrayList<Brick> creaLv3(){
        return drawLine(7, 3, 8 , 12);
    }

    public ArrayList<Brick> creaLv4(){
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

    public ArrayList<Brick> drawLine(int nColonne, int nRighe, int StartHard, int FinishHard){

        mattoncini=new ArrayList<Brick>();

      //  mattoncini = mattonciniDuri(StartHard, FinishHard,HardCol,HardColFrom );

        for(int y = 0; y < nRighe; y++ ){
            for ( int i = 0; i < nColonne; i++) { //con un ciclo creo tutti i mattoncini e li metto dentro un arraylist
                if((nMat > StartHard ) && nMat < FinishHard)
                    Brick = new Brick(shift , shiftAltezza -y*coeffy , "brick.jpg", 1);
                else {
                    Brick = new Brick(shift, shiftAltezza - y * coeffy, "normalBrick.jpg", 0);
                    nMatMorbidi++;
                }

                mattoncini.add(Brick);
                shift -= coeffx * Info.brickresize; //ogni Brick è distante dall'altro di uno shift
                nMat++;
            }
            shift = 700;
        }

        return mattoncini;
    }

    public ArrayList<Brick> mattonciniDuri(int StartHard, int FinishHard, int HardCol , int HardColFrom){

        shiftAltezza -= coeffy*FinishHard;
        shift -= coeffx*Info.brickresize*HardColFrom;

        for(int j = 0; j < StartHard; j++ ){
            for ( int z = 0; z < HardCol; z++) { //con un ciclo creo tutti i mattoncini e li metto dentro un arraylist
                Brick = new Brick(shift , shiftAltezza , "brick.jpg", 1);
                mattoncini.add(Brick);
                shift -= coeffx * Info.brickresize; //ogni Brick è distante dall'altro di uno shift

            }
            shift = 700;
        }

        shiftAltezza += coeffy*FinishHard;

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
