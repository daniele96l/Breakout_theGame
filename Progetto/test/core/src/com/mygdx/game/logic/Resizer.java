/*
* DA FARE:
*
* Deve funzionare per ogni classe di tipo Screen, è da implementare
*
*
* */

package com.mygdx.game.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Scaling;
import com.mygdx.game.help.Info;

/**
 *
 * Questa classe si occupa del ridimensionamento della finestra, più precisamete, del calcolo dei parametri
 * necessario per adattare i punti di 'click' al nuovo formato della finestra.
 *
 * @author Ligato
 */

public class Resizer {
    float barreNere;
    float coeffDimensionale;
    int newHeight;
    int newWight;
    Vector2 size;

    float tempVet[] = new float[2];

    /**
     * Il metodo toResize viene chiamato in ogni Screen ogni volta che viene ridimensionata la finestra
     * calcola i parametri necessari al funzionamento dell'interfaccia
     *
     * @param height altezza della finestra
     * @param width larghezza della finestra
     * @return ritorna i valori del coefficente dimensionale (un rapporto che indica quando si è ingrandita o diminuita la finestra)
     * e la dimensione della barre nere che dovranno essere messe ai lati per mantenere il rapporto costante
     *
     */

    public float[] toResize(int height, int width){
        barreNere = 0;
        Vector2 size = Scaling.fit.apply(Info.getInstance().getLarghezza(), Info.getInstance().getAltezza(), width, height);


        this.size = size;
        this.newHeight = height;
        this.newWight = width;

        int viewportX = (int)(width - size.x) / 2;
        int viewportY = (int)(height - size.y) / 2;
        int viewportWidth = (int)size.x;
        int viewportHeight = (int)size.y;

        coeffDimensionale = size.y/(float)Info.getInstance().getAltezza();

        if(newHeight > size.y ) {
            barreNere = Math.abs((newHeight - size.y) / 2);
        }

        Gdx.gl.glViewport(viewportX, viewportY, viewportWidth, viewportHeight);

        tempVet[0]  = barreNere;
        tempVet[1] = coeffDimensionale;

        return tempVet;

    }

}
