package com.mygdx.game.Levels;

import com.badlogic.gdx.graphics.Texture;
import eccezioni.IllegalBricksNumber;
import eccezioni.IllegalCharacter;
import help.Info;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class GestoreLivelli {
    private ArrayList<Livello> livelli;
    private int startPosX;
    private int startPosY;
    private int numBrickX;
    private int numBrickY;

    public GestoreLivelli(String nomeFile) {
        int remainder=Info.larghezza;
        numBrickX=0;
        while(remainder>=Info.getBrickWidth()+Info.brickGapX) {
            numBrickX++;
            remainder=Info.larghezza-(Info.getBrickWidth()+Info.brickGapX)*numBrickX;
        }
        startPosX=(remainder+Info.brickGapX)/2;
        remainder=Info.altezza/2;
        numBrickY=0;
        while(remainder>=Info.getBrickHeight()+Info.brickGapY) {
            numBrickY++;
            remainder=Info.altezza/2-(Info.getBrickHeight()+Info.brickGapY)*numBrickY;
        }
        startPosY=Info.altezza-remainder/2-Info.getBrickHeight();

        //Nel costruttore calcolo il numero massimo di mattoncini su entrambi gli assi;

        livelli=new ArrayList<Livello>();
        leggiFile(nomeFile);
    }

    private void leggiFile(String nomeFile) {
        try {
            FileReader reader = new FileReader(nomeFile);
            BufferedReader bufferedReader=new BufferedReader(reader);
            String buffer;
            Livello livello=new Livello(startPosX, startPosY, numBrickX, numBrickY);
            while((buffer=bufferedReader.readLine())!=null) {
                String[] bufferSplit=buffer.split(" ");
                if(bufferSplit[0].equals("background")) {
                    livello.setBackground(new Texture(bufferSplit[1]));
                }
                else {
                    if (buffer.equals("fine")) {
                        livelli.add(livello);
                        livello = new Livello(startPosX, startPosY, numBrickX, numBrickY);
                    } else {
                        if (!buffer.isEmpty()) {
                            if (!(buffer.charAt(0) == '%')) {
                                livello.addLine(buffer);
                            }
                        }
                    }
                }
            }
            livelli.add(livello);
        }
        catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        }
        catch (IllegalBricksNumber e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        }
        catch (IllegalCharacter e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        }
    }

    public Livello getLivello(int numero) {
        return livelli.get(numero);
    }

    public int getNumeroLivelli() {
        return livelli.size();
    }
}
