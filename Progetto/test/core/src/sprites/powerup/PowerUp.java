package sprites.powerup;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Player.Player;
import help.Info;
import sprites.Ball;
import sprites.Paddle;

import java.io.Serializable;

public abstract class PowerUp extends Sprite {
    protected Vector2 position;
    protected Vector2 speed;
    protected Rectangle bounds;
    protected String sound;

    public PowerUp(String image, int posX, int posY) {
        super(new Texture(image));
        this.position=new Vector2(posX, posY);
        this.speed=new Vector2(0, -Info.powerUpSpeed);
        this.bounds=new Rectangle(posX,posY,this.getWidth()*Info.powerUpResize, this.getHeight()*Info.powerUpResize);
    }

    public abstract void effect(Player player, Paddle paddle, Ball palla);

    public Rectangle getBounds() {
        return bounds;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getSpeed() {
        return speed;
    }

    public String getSound(){
        return sound;
    }

}
