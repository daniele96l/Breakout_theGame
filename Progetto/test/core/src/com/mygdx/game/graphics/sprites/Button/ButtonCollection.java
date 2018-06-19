package com.mygdx.game.graphics.sprites.Button;

import java.util.ArrayList;


/**
 * Classe che crea e contiene gli arraylist dei pulsanti presenti nel main menu.
 *
 * @author Schillaci
 */
public class ButtonCollection {
    private static ButtonCollection instance;
    private ArrayList<Button> menuButtons;
    private ArrayList<Button> pauseButtons;
    private ArrayList<Button> scoreButtons;

    private ButtonCollection() {
        menuButtons=new ArrayList<Button>();
        pauseButtons=new ArrayList<Button>();
        scoreButtons=new ArrayList<Button>();

        menuButtons.add(new Button("play.png", 530 ));
        menuButtons.add(new Button("exit.png", 50 ));
        menuButtons.add(new Button("multiplayeroffline.png", 410 ));
        menuButtons.add(new Button("multiplayeronline.png", 290 ));
        menuButtons.add(new Button("score.png", 170 ));

        pauseButtons.add(new Button("resume.png", 550));
        pauseButtons.add(new Button("exit.png", 150));
        pauseButtons.add(new Button("menu.png", 350));

        scoreButtons.add(new Button("menu.png", 50));
    }


    /**
     * Metodo che controlla e restituisce il tipo di pulsante
     *
     * @return instance
     */
    public static ButtonCollection getInstance() {
        if (instance == null) {
            instance = new ButtonCollection();
        }
        return instance;
    }

    public ArrayList<Button> getMenuButtons() {
        return menuButtons;
    }

    public ArrayList<Button> getPauseButtons() {
        return pauseButtons;
    }

    public ArrayList<Button> getScoreButtons() {
        return scoreButtons;
    }
}
