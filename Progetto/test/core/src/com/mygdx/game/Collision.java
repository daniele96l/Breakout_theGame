package com.mygdx.game;

import com.badlogic.gdx.math.Rectangle;
import sprites.ball;
import sprites.mattoncino;

public class Collision {
    private mattoncino mat;
    public  boolean eliminato;
    private ball palla;
    private boolean topCollide;

    public Collision(mattoncino mat, ball palla){
        this.mat = mat;
        this.palla = palla;
        }

    public void delete(){
        eliminato = true;
    }

    public boolean check(){
        if(collidesSide(palla.getBoundsBall())){
            delete();
            palla.getSpeedBall().set(-palla.getSpeedBall().x, palla.getSpeedBall().y);
            topCollide = true;
            return true;
        }

        if(!topCollide)
            if(collidesTopBottom(palla.getBoundsBall())){ //controllo che collida col mattoncino
                delete();            //quando abbiamo tanti mattoncini bisogna utilizzare un ciclo con un Arraylist
                palla.getSpeedBall().set(palla.getSpeedBall().x, -palla.getSpeedBall().y );
                return true;
            }
        return false;
    }
    public boolean collidesTopBottom (Rectangle boundBall){
        /////Controllo dove avviene l'impatto
        if(boundBall.overlaps(mat.getBoundsBrick())) {

            if (boundBall.y +1  >= mat.getBoundsBrick().y + mat.getBoundsBrick().height){ // il numero 1 è messo li a cazzo
                //impact from top
                return true;
            }
            if(boundBall.y  <= mat.getBoundsBrick().y){
                //impact from the bottom
                return true;
            }
        }
        return false;
    }

    public boolean collidesSide(Rectangle boundBall) {
        /////Controllo dove avviene l'impatto
        if (boundBall.overlaps(mat.getBoundsBrick())) {
            if (boundBall.x <=mat.getBoundsBrick().x) { //impact from the left
                return true;
            }
            if (boundBall.x + 4 >= mat.getBoundsBrick().x + mat.getBoundsBrick().width) { //impact from the right
                return true; //il numero 4 è messo li a cazzo ma senza non va
            }
        }
        return false;
    }

    

}
