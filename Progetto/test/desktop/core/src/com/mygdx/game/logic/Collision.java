package com.mygdx.game.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.logic.Player.Player;
import com.mygdx.game.help.Info;
import com.mygdx.game.graphics.sprites.Ball;
import com.mygdx.game.graphics.sprites.Brick.Brick;

import  com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.graphics.sprites.Paddle;
import com.mygdx.game.graphics.sprites.powerup.PowerUp;

import java.util.ArrayList;
import java.util.Date;

/**
 * La classe permette di valutare le collisioni della pallina con i diversi oggetti dello scenario, nonché con i
 * bordi dello scenario stesso.
 * E' necessario in questo caso specificare il significato del parametro "rectangle": esso è il rettangolo fittizio
 * che delimita la palla e permette di sapere se la palla ha colpito un qualunque oggetto.
 *
 * @author Regna, Schillaci
 *
 */

public class Collision {
    private Ball palla;
    private ArrayList<Brick> bricks;
    private ArrayList<PowerUp> powerUps;
    private ArrayList<Paddle> paddles;
    private ArrayList<Player> players;
    private Rectangle rectangle;
    private Music musicBrick;

    public Collision(Ball palla, ArrayList<Brick> bricks, ArrayList<PowerUp> powerUps, ArrayList<Paddle> paddles, ArrayList<Player> players) {
        this.musicBrick = Gdx.audio.newMusic(Gdx.files.internal("audio.mp3"));
        int pallaX=(int)(palla.getBoundsBall().x+palla.getSpeedBall().x* Info.getInstance().getDt());
        int pallaY=(int)(palla.getBoundsBall().y+palla.getSpeedBall().y*Info.getInstance().getDt());
        rectangle = new Rectangle(pallaX,pallaY,palla.getWidth(),palla.getHeight());
        this.palla=palla;
        this.bricks=bricks;
        this.powerUps=powerUps;
        this.paddles=paddles;
        this.players=players;
    }

    /**
     * il metodo permette di capire se sono stati colpiti i mattoncini. Seguendo passo passo:
     * viene creato inizialmente un arralist di mattoncini "eliminabili"; per ogni mattoncino dello scenario corrente,
     * viene controllato se il rettangolo fittizio che delimita la palla si sovrappone ai bordi del mattoncino: se ciò
     * avviene, il mattoncino viene aggiunto agli "eliminabili"; si procede a questo punto con l'eliminazione, ma prima
     * di ciò viene controllato se il mattoncino che sta per essere eliminato ha o meno un power up, se lo ha, il power
     * up corrispondente viene aggiunto alla lista "powerUps".
     *
     * @return il numero di mattoncini eliminati
     */
    public int checkBrickCollision() {

        ArrayList<Brick> eliminabili = new ArrayList<Brick>();
        int matEliminati=0;

        for (Brick brick : bricks) {
            if (brick.getBoundsBrick().overlaps(rectangle)) {
                eliminabili.add(brick);
            }
        }

        for(Brick brick:eliminabili) {
            if(brick.isDeletable()) {
                if(brick.hasPowerUp()) {
                    powerUps.add(brick.getPowerUp());
                }
                bricks.remove(brick);
                musicBrick.stop();
                musicBrick.play();
                matEliminati++;
            }
        }

        if (eliminabili.size() == 3) {
            palla.setSpeedBall(new Vector2(-palla.getSpeedBall().x, -palla.getSpeedBall().y));
        } else {
            if (eliminabili.size() == 2) {
                if (eliminabili.get(0).getRelativePosition(eliminabili.get(1)).equals("line")) {
                    palla.setSpeedBall(new Vector2(palla.getSpeedBall().x, -palla.getSpeedBall().y));
                } else {
                    palla.setSpeedBall(new Vector2(-palla.getSpeedBall().x, palla.getSpeedBall().y));
                }
            } else if(eliminabili.size()==1){
                evaluateSingleBrick(eliminabili.get(0));
            }
        }
        return matEliminati;
    }

    /**
     * il metodo permette di valutare le collisioni della palla con un singolo mattoncino e di modificarne la direzione
     * di conseguenza. In particolare se la palla collide con il lato del mattoncino la sua direzione rispetto l'asse y
     * viene mantenuta invariata, mentre si inverte quella relativa all'asse x. Viceversa, se la palla collide con il
     * lato superiore o inferiore del mattoncino allora la direzione che viene invertita è quella relativa all'asse y.
     *
     * @param brick il mattoncino in questione
     */
    private void evaluateSingleBrick(Brick brick) {

        if (collidesTopBottom(brick)) {
            if ((palla.getSpeedBall().y * (palla.getBoundsBall().y - (brick.getBoundsBrick().y + brick.getBoundsBrick().height / 2)) >= 0) && palla.getSpeedBall().y != 0) {
                palla.getSpeedBall().set(-palla.getSpeedBall().x, palla.getSpeedBall().y);
            }

            palla.getSpeedBall().set(palla.getSpeedBall().x, -palla.getSpeedBall().y);
        } else {
            if ((palla.getSpeedBall().x * (palla.getBoundsBall().x - (brick.getBoundsBrick().x + brick.getBoundsBrick().width / 2)) >= 0) && palla.getSpeedBall().x != 0) {
                palla.getSpeedBall().set(palla.getSpeedBall().x, -palla.getSpeedBall().y);
            }
            palla.getSpeedBall().set(-palla.getSpeedBall().x, palla.getSpeedBall().y);
        }
    }

    /**
     * il metodo permette di controllare se il lato superiore o inferiore della palla hanno toccato il bordo superiore
     * o inferiore del mattoncino passato per parametro.
     *
     * @param brick il mattoncino in questione
     * @return un boolean che vale true se la lunghezza del quadrato in cui è contenuta la palla è all'interno dei
     * seguenti valori: coordinata x del punqto in basso a sinistra del mattoncino e coordinata x del punto in basso a
     * destra.
     */
    private boolean collidesTopBottom(Brick brick) {

        return (palla.getBoundsBall().x + palla.getBoundsBall().width / 2 <= brick.getBoundsBrick().x + brick.getBoundsBrick().width) &&
                (palla.getBoundsBall().x + palla.getBoundsBall().width / 2 >= brick.getBoundsBrick().x);
    }

    /**
     * il metodo permette di controllare se la palla sta rimbalzando contro i bordi che delimitano lo scenario.
     */
    public void checkBorderCollision() {
        if(rectangle.x>Info.getInstance().getLarghezza()-palla.getWidth()*Info.getInstance().getBallresize())//controllocherimbalziadestra
            palla.getSpeedBall().set(-palla.getSpeedBall().x,palla.getSpeedBall().y);
        if(rectangle.y>Info.getInstance().getAltezza()-palla.getHeight()*Info.getInstance().getBallresize())//controllocherimbalzisu
            palla.getSpeedBall().set(palla.getSpeedBall().x,-palla.getSpeedBall().y);
        if(rectangle.x<0)
            palla.getSpeedBall().set(-palla.getSpeedBall().x,palla.getSpeedBall().y);//controllocherimbalziasinistra
    }

    /**
     * il metodo permette di controllare la collisione della palla con uno dei paddle controllati dai giocatori.
     * Per ogni paddle presente viene controllata l'eventuale collisione con la pallina, se questa è avvenuta,
     * allora il gameholder, cioè il numero del giocatore che controlla tale paddle, è l'indice corrente del paddle
     * valutato all'interno dell'arraylist dei paddle dello scenario. Viene poi reimpostata la direzione della pallina
     * in relazione all'angolo di collisione.
     *
     * @param gameHolder il numero del giocatore che sta controllando il paddle che colpisce la palla
     * @return il gameholder
     */
    public int checkPaddleCollision(int gameHolder) {

        for(Paddle paddle:paddles) {
            if (collides(palla.getBoundsBall(), paddle)) {
                gameHolder=paddles.indexOf(paddle);
                float relativeIntersectX = -((paddle.getPosition().x + (paddle.getWidth() * Info.getInstance().getPaddleresizex().get(paddle.getGiocatore() - 1) / 2)) - (palla.getPositionBall().x + palla.getWidth() * Info.getInstance().getPaddleresize() / 2));
                float normalizedRelativeIntersectionX = (relativeIntersectX / ((paddle.getTexture().getWidth() * Info.getInstance().getPaddleresizex().get(paddle.getGiocatore() - 1) / 2) + palla.getWidth() / 2));
                double MAXBOUNCEANGLE = Math.PI / 3;
                float bounceAngle = normalizedRelativeIntersectionX * (float) MAXBOUNCEANGLE;
                float speedx = (float) Math.sqrt(2 * Info.getInstance().getVelBall() * Info.getInstance().getVelBall()) * (float) (Math.sin(bounceAngle));
                float speedy = (float) Math.sqrt(2 * Info.getInstance().getVelBall() * Info.getInstance().getVelBall()) * (float) (Math.cos(bounceAngle));
                palla.getSpeedBall().set(speedx, speedy);
            }
        }
        return gameHolder;
    }

    /**
     * il metodo valuta l'eventuale collisione della pallina con il paddle paddato per parametro
     *
     * @param boundsBall il rettangolo che delimita la pallina
     * @param paddle il paddle su cui si verifica la collisione
     * @return un bollean che vale "true" quando i bordi della pallina si sovrappongono a quelli del paddle, mentre
     * vale "false" in caso contrario.
     */
    private boolean collides(Rectangle boundsBall, Paddle paddle) {
        if(boundsBall.y<3/4*paddle.getHeight()*Info.getInstance().getPaddleresize())
            return false;
        return boundsBall.overlaps(paddle.getBounds());
    }

    /**
     * il metodo permette di verificare quando un giocatore ha raccolto un power up con il proprio paddle.
     * In particolare per ogni paddle in gioco e per ogni power up racchiuso nei mattoncini dello scenario, se i bordi
     * di un rettangolo fittizio che racchiude il power up si sovrappongono a quelli del paddle in questione,
     * allora il paddle deve usufruire dell'effetto del paddle. Siccome però l'effetto ha una durata limitata, viene
     * posto, all'interno dell'arraylist di date passato per parametro, l'istante di assegnazione dell'effetto del
     * power up.
     * Viene così rimosso, una volta assegnato, il power up in questione dalla lista contenente tutti i power up
     * presenti nello scenario corrente.
     *
     * @param date l'arraylist di istanti di assegnazione dei divesrsi power up presenti nello scenario.
     */
    public void checkPowerUpCollision(ArrayList<Date> date) {
        for(Paddle paddle:paddles) {
            ArrayList<PowerUp> eliminabili=new ArrayList<PowerUp>();
            for(PowerUp powerUp:powerUps) {

                if (paddle.getBounds().overlaps(powerUp.getBounds())) {
                    powerUp.effect(players.get(paddles.indexOf(paddle)), paddle, palla);
                    eliminabili.add(powerUp);
                    Gdx.audio.newMusic(Gdx.files.internal(powerUp.getSound())).play();
                    if (Info.getInstance().getPaddleresizex().get(paddles.indexOf(paddle)) != Info.getInstance().getPaddleresize()) { // qua verifico che sia stato cambiato la resize una volta che prendo il powerup
                        date.set(paddles.indexOf(paddle), new Date());
                    }
                }
            }
            powerUps.removeAll(eliminabili);
        }
    }
}