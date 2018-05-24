package com.mygdx.game.Screen;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.BreakGame;
import help.Info;

public class Drawer
{
    public Drawer()
    {

    }
    void drawMainMenu(BreakGame game, Texture menu,Texture playButton,Texture exitButton,Texture multiplayerofflineButton,Texture score,Texture multiplayeronlineButton,int playbutton,int onlinebutton ,int offlinebutton,int scorebutton,int exitbutton)
    {
        game.getBatch().begin();
        game.getBatch().draw(menu, 0, 0);
        game.getBatch().draw(playButton, Info.larghezza / 2 - playButton.getWidth() / 2, playbutton);//alpostodimetterlicosipossousaredellecostanti
        game.getBatch().draw(exitButton, Info.larghezza / 2 - exitButton.getWidth() / 2, exitbutton);
        game.getBatch().draw(multiplayeronlineButton, Info.larghezza / 2 - multiplayerofflineButton.getWidth() / 2, offlinebutton);//immaginibruttissime
        game.getBatch().draw(score,Info.larghezza / 2 - score.getWidth() / 2, scorebutton );
        game.getBatch().draw(multiplayerofflineButton,Info.larghezza / 2 - multiplayeronlineButton.getWidth() / 2, onlinebutton );
    }
}
