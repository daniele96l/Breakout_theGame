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
import sprites.powerup.PowerUp;

public class Collision


{
    private boolean eliminato;
    private Ball palla;
    private double MAXBOUNCEANGLE=Math.PI/3;
    private Rectangle rectangle;

    public Collision(Ball palla)
    {
        int pallaX=(int)(palla.getBoundsBall().x+palla.getSpeedBall().x* Info.getInstance().getDt());
        int pallaY=(int)(palla.getBoundsBall().y+palla.getSpeedBall().y*Info.getInstance().getDt());
        rectangle = new Rectangle(pallaX,pallaY,palla.getWidth(),palla.getHeight());
        this.palla=palla;
    }

    private void delete(){
        eliminato=true;
    }

    public boolean check(AbstractBrick brick){

        if(collidesTopBottom(brick)){
            if((palla.getSpeedBall().y*(palla.getBoundsBall().y-(brick.getBoundsBrick().y+brick.getBoundsBrick().height/2))>=0)&&palla.getSpeedBall().y!=0){
                palla.getSpeedBall().set(-palla.getSpeedBall().x,palla.getSpeedBall().y);
                delete();
                return true;
            }
            delete();
            palla.getSpeedBall().set(palla.getSpeedBall().x,-palla.getSpeedBall().y);
            return true;
        }

        else{
            if(collidesSide(brick)){
                if((palla.getSpeedBall().x*(palla.getBoundsBall().x-(brick.getBoundsBrick().x+brick.getBoundsBrick().width/2))>=0)&&palla.getSpeedBall().x!=0){
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

    private boolean collidesTopBottom(AbstractBrick brick){
        if(brick.getBoundsBrick().overlaps(rectangle)){

            if((palla.getBoundsBall().x+palla.getBoundsBall().width/2<=brick.getBoundsBrick().x+brick.getBoundsBrick().width)&&
                    (palla.getBoundsBall().x+palla.getBoundsBall().width/2>=brick.getBoundsBrick().x)){
                return true;
            }
        }
        return false;
    }


    private boolean collidesSide(AbstractBrick brick){
        if(brick.getBoundsBrick().overlaps(rectangle)){
            return true;
        }
        return false;
    }

    public void checkBorderCollision(){
        if(rectangle.x>Info.getInstance().getLarghezza()-palla.getWidth()*Info.getInstance().getBallresize())//controllocherimbalziadestra
            palla.getSpeedBall().set(-palla.getSpeedBall().x,palla.getSpeedBall().y);
        if(rectangle.y>Info.getInstance().getAltezza()-palla.getHeight()*Info.getInstance().getBallresize())//controllocherimbalzisu
            palla.getSpeedBall().set(palla.getSpeedBall().x,-palla.getSpeedBall().y);
        if(rectangle.x<0)
            palla.getSpeedBall().set(-palla.getSpeedBall().x,palla.getSpeedBall().y);//controllocherimbalziasinistra
    }


    public boolean checkSide(Paddle paddle){

        if(collides(palla.getBoundsBall(),paddle)){
            float relativeIntersectX=-((paddle.getPosition().x+(paddle.getWidth()*Info.getInstance().getPaddleresizex().get(paddle.getGiocatore()-1)/2))-(palla.getPositionBall().x+palla.getWidth()*Info.getInstance().getPaddleresize()/2));
            float normalizedRelativeIntersectionX=(relativeIntersectX/((paddle.getTexture().getWidth()*Info.getInstance().getPaddleresizex().get(paddle.getGiocatore()-1)/2)+palla.getWidth()/2));
            float bounceAngle=normalizedRelativeIntersectionX*(float)MAXBOUNCEANGLE;
            float speedx=(float)Math.sqrt(2*Info.getInstance().getVelBall()*Info.getInstance().getVelBall())*(float)(Math.sin(bounceAngle));
            float speedy=(float)Math.sqrt(2*Info.getInstance().getVelBall()*Info.getInstance().getVelBall())*(float)(Math.cos(bounceAngle));
            palla.getSpeedBall().set(speedx,speedy);
            return true;
        }
        return false;
    }

    private boolean collides(Rectangle boundsBall,Paddle paddle){
        if(boundsBall.y<3/4*paddle.getHeight()*Info.getInstance().getPaddleresize())
            return false;
        return boundsBall.overlaps(paddle.getBounds());
    }

    public boolean checkPowerUp(Paddle paddle, PowerUp powerUp) {
        if(paddle.getBounds().overlaps(powerUp.getBounds())) {
            return true;
        }
        return false;
    }
}