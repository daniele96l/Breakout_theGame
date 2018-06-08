package com.mygdx.game.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.logic.Player.Player;
import com.mygdx.game.help.Info;
import com.mygdx.game.graphics.sprites.Ball;
import com.mygdx.game.graphics.sprites.Brick.Brick;

import  com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.graphics.sprites.Paddle;
import com.mygdx.game.graphics.sprites.powerup.PowerUp;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author schillaci, regna, ligato
 *
 */

public class Collision {
    private Ball palla;
    private ArrayList<Brick> bricks;
    private ArrayList<PowerUp> powerUps;
    private ArrayList<Paddle> paddles;
    private ArrayList<Player> players;
    private double MAXBOUNCEANGLE=Math.PI/3;
    private Rectangle rectangle;
    private Music musicBrick;

    public Collision(Ball palla, ArrayList<Brick> bricks, ArrayList<PowerUp> powerUps, ArrayList<Paddle> paddles, ArrayList<Player> players) {
        this.musicBrick = Gdx.audio.newMusic(Gdx.files.internal("audio.mp3"));
        int pallaX=(int)(palla.getBoundsBall().x+palla.getSpeedBall().x* Info.getInstance().getDt());
        int pallaY=(int)(palla.getBoundsBall().y+palla.getSpeedBall().y*Info.getInstance().getDt());
        rectangle = new Rectangle(pallaX,pallaY,palla.getWidth(),palla.getHeight());
        this.palla=palla;
        this.bricks=bricks;
        this.powerUps=powerUps;
        this.paddles=paddles;
        this.players=players;
    }

    /**
     * @return
     *
     *  ?? ??
     */
    public int checkBrickCollision() {

        ArrayList<Brick> eliminabili = new ArrayList<Brick>();
        int matEliminati=0;

        for (Brick brick : bricks) {
            if (brick.getBoundsBrick().overlaps(rectangle)) {
                eliminabili.add(brick);
            }
        }

        for(Brick brick:eliminabili) {
            if(brick.isDeletable()) {
                if(brick.hasPowerUp()) {
                    powerUps.add(brick.getPowerUp());
                }
                bricks.remove(brick);
                musicBrick.stop();
                musicBrick.play();
                matEliminati++;
            }
        }

        if (eliminabili.size() == 3) {
            palla.setSpeedBall(new Vector2(-palla.getSpeedBall().x, -palla.getSpeedBall().y));
        } else {
            if (eliminabili.size() == 2) {
                if (eliminabili.get(0).getRelativePosition(eliminabili.get(1)).equals("line")) {
                    palla.setSpeedBall(new Vector2(palla.getSpeedBall().x, -palla.getSpeedBall().y));
                } else {
                    palla.setSpeedBall(new Vector2(-palla.getSpeedBall().x, palla.getSpeedBall().y));
                }
            } else if(eliminabili.size()==1){
                evaluateSingleBrick(eliminabili.get(0));
            }
        }
        return matEliminati;
    }

    private void evaluateSingleBrick(Brick brick) {

        if (collidesTopBottom(brick)) {
            if ((palla.getSpeedBall().y * (palla.getBoundsBall().y - (brick.getBoundsBrick().y + brick.getBoundsBrick().height / 2)) >= 0) && palla.getSpeedBall().y != 0) {
                palla.getSpeedBall().set(-palla.getSpeedBall().x, palla.getSpeedBall().y);
            }

            palla.getSpeedBall().set(palla.getSpeedBall().x, -palla.getSpeedBall().y);
        } else {
            if ((palla.getSpeedBall().x * (palla.getBoundsBall().x - (brick.getBoundsBrick().x + brick.getBoundsBrick().width / 2)) >= 0) && palla.getSpeedBall().x != 0) {
                palla.getSpeedBall().set(palla.getSpeedBall().x, -palla.getSpeedBall().y);
            }
            palla.getSpeedBall().set(-palla.getSpeedBall().x, palla.getSpeedBall().y);
        }
    }




    private boolean collidesTopBottom(Brick brick) {

        if ((palla.getBoundsBall().x + palla.getBoundsBall().width / 2 <= brick.getBoundsBrick().x + brick.getBoundsBrick().width) &&
                (palla.getBoundsBall().x + palla.getBoundsBall().width / 2 >= brick.getBoundsBrick().x)) {
            return true;
        }
        return false;
    }


    public void checkBorderCollision() {
        if(rectangle.x>Info.getInstance().getLarghezza()-palla.getWidth()*Info.getInstance().getBallresize())//controllocherimbalziadestra
            palla.getSpeedBall().set(-palla.getSpeedBall().x,palla.getSpeedBall().y);
        if(rectangle.y>Info.getInstance().getAltezza()-palla.getHeight()*Info.getInstance().getBallresize())//controllocherimbalzisu
            palla.getSpeedBall().set(palla.getSpeedBall().x,-palla.getSpeedBall().y);
        if(rectangle.x<0)
            palla.getSpeedBall().set(-palla.getSpeedBall().x,palla.getSpeedBall().y);//controllocherimbalziasinistra
    }


    public int checkPaddleCollision(int gameHolder) {

        for(Paddle paddle:paddles) {
            if (collides(palla.getBoundsBall(), paddle)) {
                gameHolder=paddles.indexOf(paddle);
                float relativeIntersectX = -((paddle.getPosition().x + (paddle.getWidth() * Info.getInstance().getPaddleresizex().get(paddle.getGiocatore() - 1) / 2)) - (palla.getPositionBall().x + palla.getWidth() * Info.getInstance().getPaddleresize() / 2));
                float normalizedRelativeIntersectionX = (relativeIntersectX / ((paddle.getTexture().getWidth() * Info.getInstance().getPaddleresizex().get(paddle.getGiocatore() - 1) / 2) + palla.getWidth() / 2));
                float bounceAngle = normalizedRelativeIntersectionX * (float) MAXBOUNCEANGLE;
                float speedx = (float) Math.sqrt(2 * Info.getInstance().getVelBall() * Info.getInstance().getVelBall()) * (float) (Math.sin(bounceAngle));
                float speedy = (float) Math.sqrt(2 * Info.getInstance().getVelBall() * Info.getInstance().getVelBall()) * (float) (Math.cos(bounceAngle));
                palla.getSpeedBall().set(speedx, speedy);
            }
        }
        return gameHolder;
    }

    private boolean collides(Rectangle boundsBall,Paddle paddle) {
        if(boundsBall.y<3/4*paddle.getHeight()*Info.getInstance().getPaddleresize())
            return false;
        return boundsBall.overlaps(paddle.getBounds());
    }

    public void checkPowerUpCollision(ArrayList<Date> date) {
        for(Paddle paddle:paddles) {
            ArrayList<PowerUp> eliminabili=new ArrayList<PowerUp>();
            for(PowerUp powerUp:powerUps) {

                if (paddle.getBounds().overlaps(powerUp.getBounds())) {
                    powerUp.effect(players.get(paddles.indexOf(paddle)), paddle, palla);
                    eliminabili.add(powerUp);
                    Gdx.audio.newMusic(Gdx.files.internal(powerUp.getSound())).play();
                    if (Info.getInstance().getPaddleresizex().get(paddles.indexOf(paddle)) != Info.getInstance().getPaddleresize()) { // qua verifico che sia stato cambiato la resize una volta che prendo il powerup
                        date.set(paddles.indexOf(paddle), new Date());
                    }
                }
            }
            powerUps.removeAll(eliminabili);
        }
    }
}