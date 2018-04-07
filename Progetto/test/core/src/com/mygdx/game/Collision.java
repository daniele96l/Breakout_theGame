package com.mygdx.game;

import Help.Info;
import com.badlogic.gdx.math.Rectangle;
import sprites.ball;
import sprites.mattoncino;
import sprites.mattonella;

public class Collision {
    private mattoncino mat;
    public  boolean eliminato;
    private ball palla;
    private boolean topCollide;
    static int eliminati; //deve rimanere static!

    public Collision(mattoncino mat, ball palla){
        this.mat = mat;
        this.palla = palla;
        }

    public void delete(){
        eliminato = true;
        eliminati ++; //controllo quanti mattoncini ho eliminato, mi serve per controllare se passare al prossimo livello

    }



    //questo metodo serve per la collisione con i brick verificando se collidono, nel caso cambio il verso della palla
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

    //metodo usato per il check(quando tocca o sopra o sotto)
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

    //metodo usato per il check(quando tocca a destra o sinistra)
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

    // questo metodo serve per controllare quando la palla collide con i bordi o con la mattonella
    public void checkside(float dt, mattonella mattonella){
        palla.getPositionBall().add(palla.getSpeedBall().x *dt, palla.getSpeedBall().y*dt);
        palla.getBoundsBall().setPosition(palla.getPositionBall().x, palla.getPositionBall().y);

        if(palla.getPositionBall().x > Info.larghezza- 30) //controllo che rimbalzi a destra
            palla.getSpeedBall().set(-palla.getSpeedBall().x, palla.getSpeedBall().y);
        if(palla.getPositionBall().y > Info.altezza -30) //controllo che rimbalzi su
            palla.getSpeedBall().set(palla.getSpeedBall().x,-palla.getSpeedBall().y);
        if(palla.getPositionBall().x < 0)
            palla.getSpeedBall().set(-palla.getSpeedBall().x, palla.getSpeedBall().y); //controllo che rimbalzi a sinistra

        if(collides(palla.getBoundsBall(), mattonella)) //controllo che collida con la mattonella//
            {
            palla.getSpeedBall().set( palla.getSpeedBall().x, -palla.getSpeedBall().y);
        }

    }

    private boolean collides(Rectangle boundsBall, mattonella mattonella){
        if(boundsBall.y < 20) //ovvero se la palla scende sotto il bordo superiose e colpisce il lato della mattonella  non rimalza ma va dritta giu
            return false;  //dato che ha mancato la parte superiore piana è impossibile che venga rimbalzata su
        //serve anche ad evitare un bug che faceva entrare la pallina dentro la mattonella
        return boundsBall.overlaps(mattonella.getBounds()); //la funzione che controllerà se la pallina tocca la mattonella
    }

    public int getEliminati() {
        return eliminati;
    }

    public void setEliminati(int eliminati) {
        Collision.eliminati = 0;
    }
}
