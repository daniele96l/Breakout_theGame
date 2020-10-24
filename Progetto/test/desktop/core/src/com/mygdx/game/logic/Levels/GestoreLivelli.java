package com.mygdx.game.logic.Levels;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.eccezioni.IllegalBricksNumber;
import com.mygdx.game.eccezioni.IllegalCharacter;
import com.mygdx.game.help.Info;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * la classe gestisce i diversi livelli del gioco leggendo dai file in cui è specificata la struttura dei livelli
 * PATTERN PURE FABRICATION
 *
 * @author Ligato, Schillaci, Regna
 */

public class GestoreLivelli {
    private ArrayList<Livello> livelli;
    private int startPosX;
    private int startPosY;
    private int numBrickX;
    private int numBrickY;

    public GestoreLivelli(String nomeFile) {
        int remainder=Info.getInstance().getLarghezza();
        numBrickX=0;
        while(remainder>=Info.getInstance().getBrickWidth()+Info.getInstance().getBrickGapX()) {
            numBrickX++;
            remainder=Info.getInstance().getLarghezza()-(Info.getInstance().getBrickWidth()+Info.getInstance().getBrickGapX())*numBrickX;
        }
        startPosX=(remainder+Info.getInstance().getBrickGapX())/2;
        remainder=Info.getInstance().getAltezza()/2;
        numBrickY=0;
        while(remainder>=Info.getInstance().getBrickHeight()+Info.getInstance().getBrickGapY()) {
            numBrickY++;
            remainder=Info.getInstance().getAltezza()/2-(Info.getInstance().getBrickHeight()+Info.getInstance().getBrickGapY())*numBrickY;
        }
        startPosY=Info.getInstance().getAltezza()-remainder/2-Info.getInstance().getBrickHeight()-Info.getInstance().getHudHeight();

        //Nel costruttore calcolo il numero massimo di mattoncini su entrambi gli assi;

        livelli=new ArrayList<Livello>();
        leggiFile(nomeFile);
    }

    /**
     * è il metodo che permette di creare il livello corrispondente leggendo dal file dei livelli che gli viene passato come parametro
     *
     * @param nomeFile
     *
     * @exception FileNotFoundException
     * @exception IOException
     * @exception IllegalBricksNumber
     * @exception IllegalCharacter
     *
     *
     *
     */
    private void leggiFile(String nomeFile) {
        try {
            FileReader reader = new FileReader(nomeFile);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String buffer;
            Livello livello = new Livello(startPosX, startPosY, numBrickX, numBrickY);
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
