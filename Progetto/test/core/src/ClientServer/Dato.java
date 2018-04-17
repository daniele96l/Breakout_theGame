package ClientServer;

import sprites.Ball;
import sprites.Brick.AbstractBrick;
import sprites.Paddle;

import java.util.ArrayList;

public class Dato
{
   private Ball palla;
   private Paddle  paddle;
   private ArrayList<AbstractBrick> mattoncini;
   public Dato(Ball palla, Paddle paddle, ArrayList<AbstractBrick> mattoncini)
   {
       this.paddle=paddle;
       this.palla=palla;
       this.mattoncini=mattoncini;
   }

    public Ball getPalla() {
        return palla;
    }

    public Paddle getPaddle() {
        return paddle;
    }

    public ArrayList<AbstractBrick> getMattoncini() {
        return mattoncini;
    }
}
