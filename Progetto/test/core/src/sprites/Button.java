package sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import help.Info;

public class Button extends Sprite {
    public Button(String path, int posY) {
        super(new Texture(path));
        setPosition(Info.getInstance().getLarghezza()/2-getWidth()/2, posY);
    }
}
