package com.mygdx.game.logic;

import com.badlogic.gdx.Input;
import com.mygdx.game.Player.Player;
import help.Info;
import sprites.Paddle;

public class MovePlayer {

    public void MoveIt(int key , int numeroGiocatori, int giocatore, Paddle paddle){
        if(key== Input.Keys.LEFT){
            if(paddle.getPosition().x >(Info.larghezza/numeroGiocatori)*(giocatore-1)) { //controllo il range in cui la Paddle si può muovere

                paddle.getPosition().add(-10, 0);
                paddle.getBounds().setPosition(paddle.getPosition().x, paddle.getPosition().y);
            }
        }
        if(key==Input.Keys.RIGHT){
            if(paddle.getPosition().x < ((Info.larghezza/numeroGiocatori)*(giocatore))- paddle.getWidth()* Info.paddleresizex.get(giocatore-1) ) {
                paddle.getPosition().add(10, 0);//controllo il range in cui la Paddle si può muovere
                paddle.getBounds().setPosition(paddle.getPosition().x, paddle.getPosition().y);
            }
        }

    }

    public void MoveRobot(int numeroGiocatori, int giocatore, Paddle paddle, Player player){

        if(player.keyPressed()==Input.Keys.LEFT){
            if(paddle.getPosition().x >(Info.larghezza/numeroGiocatori)*(giocatore-1)) { //controllo il range in cui la Paddle si può muovere
                paddle.getPosition().add(-Info.getVelPaddle(), 0);
                paddle.getBounds().setPosition(paddle.getPosition().x, paddle.getPosition().y);
            }
        }
        if(player.keyPressed()==Input.Keys.RIGHT){
            if(paddle.getPosition().x < ((Info.larghezza/numeroGiocatori)*(giocatore))- paddle.getWidth()* Info.paddleresizex.get(giocatore-1) ) {
                paddle.getPosition().add(Info.getVelPaddle(), 0);//controllo il range in cui la Paddle si può muovere
                paddle.getBounds().setPosition(paddle.getPosition().x, paddle.getPosition().y);
            }
        }

    }
}
