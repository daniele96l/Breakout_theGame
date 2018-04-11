package com.mygdx.game;

import com.badlogic.gdx.math.Rectangle;
import sprites.Ball;
import sprites.Brick;
import sprites.Paddle;

public class Collision {
    private Brick mat;
    private boolean eliminato;
    private Ball palla;
    private boolean topCollide;
    private double MAXBOUNCEANGLE = Math.PI/3;

    public Collision(Brick mat, Ball palla){
        this.mat = mat;
        this.palla = palla;
        }

    public void delete(){
        eliminato = true;
    }

    //questo metodo serve per la collisione con i brick verificando se collidono, nel caso cambio il verso della palla
    public boolean check(){

        if(collidesTopBottom(palla.getBoundsBall())){ //controllo che collida col Brick
            if((palla.getPositionBall().y-mat.getBoundsBrick().y)*palla.getSpeedBall().y>=0) {
                delete();
                palla.getSpeedBall().set(-palla.getSpeedBall().x, palla.getSpeedBall().y);
                System.out.println(palla.getPositionBall().y);
                System.out.println(mat.getBoundsBrick().y);
                System.out.println(palla.getSpeedBall().y);
                return true;
            }
            delete();            //quando abbiamo tanti mattoncini bisogna utilizzare un ciclo con un Arraylist
            palla.getSpeedBall().set(palla.getSpeedBall().x, -palla.getSpeedBall().y );
            return true;
        }
        else {
            if(collidesSide(palla.getBoundsBall())) {
                if((palla.getPositionBall().x-(mat.getBoundsBrick().x+mat.getBoundsBrick().width/2)*palla.getSpeedBall().x)>=0) {
                    delete();
                    palla.getSpeedBall().set(palla.getSpeedBall().x, -palla.getSpeedBall().y );
                    return true;
                }
                delete();
                palla.getSpeedBall().set(-palla.getSpeedBall().x, palla.getSpeedBall().y);
                return true;
            }
            return false;
        }
    }

    //metodo usato per il check(quando tocca o sopra o sotto)
    public boolean collidesTopBottom (Rectangle boundBall){
        /////Controllo dove avviene l'impatto

        if(boundBall.overlaps(mat.getBoundsBrick())) {

            if ((boundBall.x + boundBall.width/2 >= mat.getBoundsBrick().x) && (boundBall.x + boundBall.width/2 <= mat.getBoundsBrick().x + mat.getBoundsBrick().width)){
                return true;
            }
        }
        return false;
    }

    //metodo usato per il check(quando tocca a destra o sinistra)
    public boolean collidesSide(Rectangle boundBall) {
        /////Controllo dove avviene l'impatto
        if (boundBall.overlaps(mat.getBoundsBrick()))
            return true;
        return false;
    }

    // questo metodo serve per controllare quando la palla collide con i bordi o con la Paddle
    public void checkside(Paddle paddle){

        if(palla.getPositionBall().x > Info.larghezza- palla.getWidth()* Info.ballresize) //controllo che rimbalzi a destra
            palla.getSpeedBall().set(-palla.getSpeedBall().x, palla.getSpeedBall().y);
        if(palla.getPositionBall().y > Info.altezza - palla.getHeight() * Info.ballresize) //controllo che rimbalzi su
            palla.getSpeedBall().set(palla.getSpeedBall().x,-palla.getSpeedBall().y);
        if(palla.getPositionBall().x < 0)
            palla.getSpeedBall().set(-palla.getSpeedBall().x, palla.getSpeedBall().y); //controllo che rimbalzi a sinistra

        if(collides(palla.getBoundsBall(), paddle)) { //controllo che collida con la Paddle
            float relativeIntersectX = -((paddle.getPosition().x + (paddle.getWidth()*Info.paddleresize / 2)) - (palla.getPositionBall().x+palla.getWidth()*Info.paddleresize/2));
            float normalizedRelativeIntersectionX = (relativeIntersectX / ((paddle.getTexture().getWidth()*Info.paddleresize / 2)+palla.getWidth()/2));
            float bounceAngle = normalizedRelativeIntersectionX * (float)MAXBOUNCEANGLE;
            float speedx = (float) Math.sqrt(2*Info.velBall*Info.velBall)*(float) (Math.sin(bounceAngle));
            float speedy = (float) Math.sqrt(2*Info.velBall*Info.velBall)*(float) (Math.cos(bounceAngle));
            palla.getSpeedBall().set(speedx,speedy);
        }
    }

    private boolean collides(Rectangle boundsBall, Paddle paddle){
        if(boundsBall.y < 3/4*paddle.getHeight()*Info.paddleresize) //ovvero se la palla scende sotto il bordo superiose e colpisce il lato della Paddle  non rimalza ma va dritta giu
            return false;  //dato che ha mancato la parte superiore piana è impossibile che venga rimbalzata su
        //serve anche ad evitare un bug che faceva entrare la pallina dentro la Paddle
        return boundsBall.overlaps(paddle.getBounds()); //la funzione che controllerà se la pallina tocca la Paddle
    }
}
