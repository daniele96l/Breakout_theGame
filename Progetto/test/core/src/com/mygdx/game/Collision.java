package com.mygdx.game;

import com.badlogic.gdx.audio.Music;
import help.Info;
import sprites.Ball;
import sprites.Brick.AbstractBrick;

import  com.badlogic.gdx.math.Rectangle;
import help.Info;
import sprites.Ball;
import sprites.Brick.AbstractBrick;
import sprites.Paddle;

public class Collision


{
    private AbstractBrick mat;
    private boolean eliminato;
    private Ball palla;
    private double MAXBOUNCEANGLE=Math.PI/3;
    private Rectangle rectangle;

    public Collision(AbstractBrick mat,Ball palla)
    {

        int pallaX=(int)(palla.getBoundsBall().x+palla.getSpeedBall().x* Info.dt);
        int pallaY=(int)(palla.getBoundsBall().y+palla.getSpeedBall().y*Info.dt);
        rectangle = new Rectangle(pallaX,pallaY,palla.getWidth(),palla.getHeight());
        this.mat=mat;
        this.palla=palla;
    }

    public void delete(){
        eliminato=true;
    }

//questometodoserveperlacollisioneconibrickverificandosecollidono,nelcasocambioilversodellapalla
    public boolean check(){

        if(collidesTopBottom()){//controllochecollidacolBrick
            if((palla.getSpeedBall().y*(palla.getBoundsBall().y-(mat.getBoundsBrick().y+mat.getBoundsBrick().height/2))>=0)&&palla.getSpeedBall().y!=0){
                palla.getSpeedBall().set(-palla.getSpeedBall().x,palla.getSpeedBall().y);
                delete();
                return true;
            }
            delete();//quandoabbiamotantimattoncinibisognautilizzareuncicloconunArraylist
            palla.getSpeedBall().set(palla.getSpeedBall().x,-palla.getSpeedBall().y);
            return true;
        }

        else{
            if(collidesSide()){
                if((palla.getSpeedBall().x*(palla.getBoundsBall().x-(mat.getBoundsBrick().x+mat.getBoundsBrick().width/2))>=0)&&palla.getSpeedBall().x!=0){
                    palla.getSpeedBall().set(palla.getSpeedBall().x,-palla.getSpeedBall().y);
                    delete();
                    return true;
                }
                delete();
                palla.getSpeedBall().set(-palla.getSpeedBall().x,palla.getSpeedBall().y);
                return true;
            }
        }

        return false;
    }

//metodousatoperilcheck(quandotoccaosopraosotto)
    public boolean collidesTopBottom(){
/////Controllodoveavvienel'impatto
        if(mat.getBoundsBrick().overlaps(rectangle)){

            if((palla.getBoundsBall().x+palla.getBoundsBall().width/2<=mat.getBoundsBrick().x+mat.getBoundsBrick().width)&&
                    (palla.getBoundsBall().x+palla.getBoundsBall().width/2>=mat.getBoundsBrick().x)){
                return true;
            }
        }
        return false;
    }


    //metodousatoperilcheck(quandotoccaadestraosinistra)
    public boolean collidesSide(){
/////Controllodoveavvienel'impatto
        if(mat.getBoundsBrick().overlaps(rectangle)){
            return true;
        }
        return false;
    }

    public void checkBorderCollision(){
        if(rectangle.x>Info.larghezza-palla.getWidth()*Info.ballresize)//controllocherimbalziadestra
            palla.getSpeedBall().set(-palla.getSpeedBall().x,palla.getSpeedBall().y);
        if(rectangle.y>Info.altezza-palla.getHeight()*Info.ballresize)//controllocherimbalzisu
            palla.getSpeedBall().set(palla.getSpeedBall().x,-palla.getSpeedBall().y);
        if(rectangle.x<0)
            palla.getSpeedBall().set(-palla.getSpeedBall().x,palla.getSpeedBall().y);//controllocherimbalziasinistra
    }


    //questometodoservepercontrollarequandolapallacollideconibordioconlaPaddle
    public boolean checkSide(Paddle paddle){

        if(collides(palla.getBoundsBall(),paddle)){//controllochecollidaconlaPaddle
            float relativeIntersectX=-((paddle.getPosition().x+(paddle.getWidth()*Info.paddleresize/2))-(palla.getPositionBall().x+palla.getWidth()*Info.paddleresize/2));
            float normalizedRelativeIntersectionX=(relativeIntersectX/((paddle.getTexture().getWidth()*Info.paddleresize/2)+palla.getWidth()/2));
            float bounceAngle=normalizedRelativeIntersectionX*(float)MAXBOUNCEANGLE;
            float speedx=(float)Math.sqrt(2*Info.velBall*Info.velBall)*(float)(Math.sin(bounceAngle));
            float speedy=(float)Math.sqrt(2*Info.velBall*Info.velBall)*(float)(Math.cos(bounceAngle));
            palla.getSpeedBall().set(speedx,speedy);
            return true;
        }
        return false;
    }

    private boolean collides(Rectangle boundsBall,Paddle paddle){
        if(boundsBall.y<3/4*paddle.getHeight()*Info.paddleresize)//ovveroselapallascendesottoilbordosuperioseecolpisceillatodellaPaddlenonrimalzamavadrittagiu
            return false;//datochehamancatolapartesuperiorepianaèimpossibilechevengarimbalzatasu
//serveancheadevitareunbugchefacevaentrarelapallinadentrolaPaddle
        return boundsBall.overlaps(paddle.getBounds());//lafunzionechecontrolleràselapallinatoccalaPaddle
    }
}