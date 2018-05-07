package com.mygdx.game;

import com.badlogic.gdx.Input;
import com.mygdx.game.Player.Player;
import help.Info;
import sprites.Paddle;

public class CommandPlayer {

    private Paddle paddle;
    private Player player;
    int numeroGiocatori;
    int giocatore;

    public CommandPlayer(Paddle paddle, Player player, int numeroGiocatori, int giocatore){
        this.paddle = paddle;
        this.player=player;
        this.numeroGiocatori=numeroGiocatori;
        this.giocatore=giocatore;
    }


    public void move()
    {
        if(player.keyPressed()==Input.Keys.LEFT){
            if(paddle.getPosition().x >(Info.larghezza/numeroGiocatori)*(giocatore-1)) { //controllo il range in cui la Paddle si può muovere
                paddle.getPosition().add(-10, 0);
                paddle.getBounds().setPosition(paddle.getPosition().x, paddle.getPosition().y);
            }
        }
        if(player.keyPressed()==Input.Keys.RIGHT){
            if(paddle.getPosition().x < ((Info.larghezza/numeroGiocatori)*(giocatore))- paddle.getWidth()* Info.paddleresizex.get(giocatore-1) ) {
                paddle.getPosition().add(10, 0);//controllo il range in cui la Paddle si può muovere
                paddle.getBounds().setPosition(paddle.getPosition().x, paddle.getPosition().y);
            }
        }
    }

    public boolean checkpause(){
        if(player.keyPressed()==Input.Keys.P){
            return true;
        }
        return false;
    }
}
