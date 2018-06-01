/*
* DA FARE:
*
* Trovare qualche pattern da implementare nel metodo insertPowerUp che sfrutti il polimorfismo
*
*
*
*
* */

package com.mygdx.game.Levels;

import com.badlogic.gdx.graphics.Texture;
import eccezioni.IllegalBricksNumber;
import eccezioni.IllegalCharacter;
import eccezioni.IllegalPowerUp;
import help.Info;
import sprites.Brick.Brick;
import sprites.Brick.NormalBrick;
import sprites.Brick.HardBrick;
import sprites.SpriteFactory;

import java.util.ArrayList;

/**
 * La classe permette di definire la struttura del livello instanziando gli oggetti che ne fanno parte
 *
 * @author Schillaci
 *
 */
public class Livello {
    private ArrayList<Brick> bricks;
    private int currentPosX;
    private int currentPosY;
    private int startPosX;
    private int startPosY;
    private int numBrickX;
    private int numBrickY;
    private int linesAdded;
    private int nMatMorbidi;
    private Texture background;

    public Livello(int startPosX, int startPosY, int numBrickX, int numBrickY) {
        this.startPosX = startPosX;
        this.startPosY = startPosY;
        this.numBrickX=numBrickX;
        this.numBrickY=numBrickY;

        bricks=new ArrayList<Brick>();
        background=new Texture("bg.jpg");   //Scelta di default

        currentPosY=startPosY;
        linesAdded=0;
        nMatMorbidi=0;
    }

    /**
     * aggiunge una riga di mattoncini
     *
     * @param line
     * @throws IllegalBricksNumber
     * @throws IllegalCharacter
     */
    public void addLine(String line) throws IllegalBricksNumber, IllegalCharacter {
        linesAdded++;
        if (linesAdded > numBrickY || line.length() != numBrickX)
            throw new IllegalBricksNumber(numBrickX, numBrickY);
        currentPosX = startPosX;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            switch (c) {
                case '.':
                    break;
                case '-':
                    bricks.add(new NormalBrick(currentPosX, currentPosY));
                    insertPowerUp(bricks.get(bricks.size()-1));
                    nMatMorbidi++;
                    break;
                case '#':
                    bricks.add(new HardBrick(currentPosX, currentPosY));
                    break;
                default:
                    throw new IllegalCharacter(c);
            }
            currentPosX += Info.getInstance().getBrickWidth()+Info.getInstance().getBrickGapX();
        }
        currentPosY -= Info.getInstance().getBrickHeight()+Info.getInstance().getBrickGapY();
    }

    /**
     * aggiunge i power up in maniera casuale ai mattoncini del livello
     *
     * @param brick
     */
    private void insertPowerUp(Brick brick) {
        int posX=(int)(brick.getBoundsBrick().x+brick.getBoundsBrick().width/2-Info.getInstance().getPowerUpWidth()/2*Info.getInstance().getPowerUpResize());
        int posY=(int)(brick.getBoundsBrick().y+brick.getBoundsBrick().height/2-Info.getInstance().getPowerUpHeight()/2*Info.getInstance().getPowerUpResize());

        float randNum=(float)(Math.random()*10);
        if(randNum < Info.getInstance().getPowerUpChance()) {
            float interval=Info.getInstance().getPowerUpChance()/(float)Info.getInstance().getNumeroPowerUp();
            try {
                if (randNum >= 0 && randNum < interval) {
                    brick.setPowerUp(SpriteFactory.getInstance().getPowerUp("ExtraLife", posX, posY));
                }

                if (randNum >= interval && randNum < 2 * interval) {
                    brick.setPowerUp(SpriteFactory.getInstance().getPowerUp("LostLife", posX, posY));

                }

                if (randNum >= 2 * interval && randNum < 3 * interval) {
                    brick.setPowerUp(SpriteFactory.getInstance().getPowerUp("LongPaddle", posX, posY));
                }

                if (randNum >= 3 * interval && randNum < 4 * interval) {
                    brick.setPowerUp(SpriteFactory.getInstance().getPowerUp("ShortPaddle", posX, posY));

                }
                //////Inserire nuovi power up qua
            }
            catch (IllegalPowerUp exc) {
                System.err.println(exc.getMessage());
                System.exit(-1);
            }
        }
    }

    public ArrayList<Brick> getBricks() {
        return bricks;
    }

    public int getnMatMorbidi() {
        return nMatMorbidi;
    }

    public void setBackground(Texture background) {
        this.background = background;
    }

    public Texture getBackground() {
        return background;
    }
}

