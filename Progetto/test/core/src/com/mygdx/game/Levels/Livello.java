package com.mygdx.game.Levels;

import com.badlogic.gdx.graphics.Texture;
import eccezioni.IllegalBricksNumber;
import eccezioni.IllegalCharacter;
import help.Info;
import sprites.Brick.AbstractBrick;
import sprites.Brick.Brick;
import sprites.Brick.HardBrick;

import java.util.ArrayList;

public class Livello {
    private ArrayList<AbstractBrick> bricks;
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

        bricks=new ArrayList<AbstractBrick>();
        background=new Texture("bg.jpg");   //Scelta di default

        currentPosY=startPosY;
        linesAdded=0;
        nMatMorbidi=0;
    }

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
                    bricks.add(new Brick(currentPosX, currentPosY));
                    nMatMorbidi++;
                    break;
                case '#':
                    bricks.add(new HardBrick(currentPosX, currentPosY));
                    break;
                default:
                    throw new IllegalCharacter(c);
            }
            currentPosX += Info.getBrickWidth()+Info.brickGapX;
        }
        currentPosY -= Info.getBrickHeight()+Info.brickGapY;
    }

    public ArrayList<AbstractBrick> getBricks() {
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

