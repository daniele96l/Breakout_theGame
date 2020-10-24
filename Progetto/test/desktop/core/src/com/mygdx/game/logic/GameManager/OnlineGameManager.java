package com.mygdx.game.logic.GameManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.FileTextureData;
import com.mygdx.game.clientserver.Server;
import com.mygdx.game.logic.Player.CommandPlayer;
import com.mygdx.game.logic.Levels.GestoreLivelli;
import com.mygdx.game.logic.Player.HumanPlayer;
import com.mygdx.game.logic.Player.Player;
import com.mygdx.game.help.GameState;
import com.mygdx.game.help.Info;
import com.mygdx.game.help.Timer;
import com.mygdx.game.graphics.sprites.Ball;
import com.mygdx.game.graphics.sprites.Brick.Brick;
import com.mygdx.game.graphics.sprites.Paddle;
import com.mygdx.game.graphics.sprites.powerup.PowerUp;
import java.util.ArrayList;
import java.util.Date;

/**
 * Questa classe si occupa di gestire il gioco quando lo si sta usando in modalità Online, gestisce infatti
 * come si dovrà comportare l'interfaccia a seconda degli avvenimenti, come il numero di giocatori, o le
 * interazioni con i mattoncini.
 *
 * @author schillaci, regna
 */

public class OnlineGameManager extends GameManager {

    private boolean creato;
    private Date datetmp;
    private Server server;

    public OnlineGameManager(ArrayList<String> names, Server server) {
        numeroPlayer=names.size();
        this.server=server;

        players = new ArrayList<Player>();
        paddles = new ArrayList<Paddle>();
        date = new ArrayList<Date>();
        commandPlayers = new ArrayList<CommandPlayer>();
        creato = false;

        timer = new Timer();

        for (int i = 0; i < numeroPlayer; i++) {
            players.add(new HumanPlayer(names.get(i)));
            paddles.add(new Paddle(numeroPlayer, i + 1));
            Info.getInstance().getPaddleresizex().add(0.5f);
            commandPlayers.add(new CommandPlayer(paddles.get(i), players.get(i), numeroPlayer, i + 1));
        }

        livelloCorrente = 1;
        nextLevel = false;
        palla = new Ball();
        tmpDT = Info.getInstance().getDt();

        for (int i = 0; i < numeroPlayer; i++) {
            date.add(new Date());
        }

        gestoreLivelli = new GestoreLivelli("fileLivelli.txt");

        updateScene();
        updateLevel();
        gameState=GameState.WAIT;
        gameHolder = players.get(0);
        bg = gestoreLivelli.getLivello(livelloCorrente - 1).getBackground();
    }

    /**
     * Si aggiorna 60 volte al secondo e aggiorna le posizione degli oggetti.
     * Gestisce i movimenti del player;
     * invia le informazioni al server;
     * gestisce le collisioni della pallina;
     * controlla la durata dei power up;
     * permette di capire se è stato superato il livello;
     *
     * @see GameManager per il "metodo gestiscicollisioni()"
     *
     */
    public void render() {
        if (nextLevel) {//deve stare dentro render perchè deve essere controllato sempre
            bricks = gestoreLivelli.getLivello(livelloCorrente - 1).getBricks();//ritorno l'array adatto al nuovo livello
            bg = gestoreLivelli.getLivello(livelloCorrente - 1).getBackground();

        }

        palla.getPositionBall().add(palla.getSpeedBall().x * Info.getInstance().getDt(), palla.getSpeedBall().y * Info.getInstance().getDt());
        palla.getBoundsBall().setPosition(palla.getPositionBall());
        ArrayList<PowerUp> tmpPUps = new ArrayList<PowerUp>();
        for (PowerUp p : powerUps) {
            if (p.getPosition().y + Info.getInstance().getPowerUpHeight() < 30) {
                tmpPUps.add(p);
            }
            p.getPosition().add(p.getSpeed().x * Info.getInstance().getDt(), p.getSpeed().y * Info.getInstance().getDt());
            p.getBounds().setPosition(p.getPosition());
        }
        for (PowerUp p : tmpPUps) {
            powerUps.remove(p);
        }

        for (int i = 0; i < commandPlayers.size(); i++) {
            server.movePlayer(commandPlayers.get(i), i);
        }

        writeMessage();

        gestisciCollisioni();


        checkTimerPowerUp(); // controlla il tempo

        if (matEliminati == gestoreLivelli.getLivello(livelloCorrente - 1).getnMatMorbidi()) {
            gameState = GameState.YOU_WON;

            livelloCorrente++;
            if (livelloCorrente > gestoreLivelli.getNumeroLivelli()) {
                isFinished = true;
            }
        }

        if (palla.getPositionBall().y <= 0) {
            lostLife();
            updateScene();
        }


        if (gameState == GameState.YOU_WON) {
            if (isFinished) {
                livelloCorrente = 1;
                isFinished = false;
                updateScene();
                updateLevel();
            } else {
                nextLevel = true;
                updateScene();
                updateLevel();
            }
            gameState = GameState.WAIT;
        }

        if (gameState == GameState.WAIT) {
            Info.getInstance().setDt(0);
            if (!creato) {
                datetmp = new Date();
                creato = true;
            }
            if (checktimer(3000, datetmp)) {
                Info.getInstance().setDt(tmpDT);
                gameState = GameState.ACTION;
                creato = false;
            }
        }
    }

    /**
     * il metodo permette di inviare informazioni al server relative al gioco. In particolare con il seguente
     * metodo si costruisce un messaggio composto da diverse parti separate da "\t".
     * Se il numero di giocatori ancora in gioco è pari a 0, il messaggio da inviare è "empty".
     * Se ciò non accade, il metodo costruisce il messaggio inserendo le informazioni relative a:
     * - posizione della palla;
     * - posizione dei paddle;
     * - nome dei giocatori, punteggio e vite;
     * - posizione dei mattoncini;
     * - info sui power Up.
     *
     */

    private void writeMessage() {

        StringBuilder message = new StringBuilder();
        if (numeroPlayer == 0) {
            message = new StringBuilder("Empty");
        } else {
            message.append(palla.getPositionBall().x).append(" ").append(palla.getPositionBall().y).append("\t");
            for (Paddle paddle : paddles) {
                message.append(paddle.getPosition().x).append(" ");
            }
            message.append("\t");

            for (float f : Info.getInstance().getPaddleresizex()) {
                message.append(f).append(" ");
            }
            message.append("\t");

            for (Player player : players) {
                message.append(player.getPlayerName()).append(" ");
                message.append(player.getScore()).append(" ");
                message.append(player.getLives()).append(" ");
                message.append("\t");
            }

            for (Brick brick : bricks) {
                message.append(brick.getPositionBrick().x).append(" ").append(brick.getPositionBrick().y).append(" ");
                message.append(brick.getClass().getSimpleName()).append("\t");
            }
            for (PowerUp powerUp : powerUps) {
                message.append(powerUp.getPosition().x).append(" ").append(powerUp.getPosition().y).append(" ");
                message.append(powerUp.getClass().getSimpleName()).append("\t");
            }

            String bgPath = ((FileTextureData) bg.getTextureData()).getFileHandle().name();

            message.append(bgPath).append("\t");


            message = new StringBuilder(message.substring(0, message.length() - 1));
        }
        byte[] bytes = message.toString().getBytes();

        server.sendMessage(bytes);

        if (numeroPlayer == 0) {
            Gdx.app.exit();
        }
    }

    /**
     * il metodo permette di fare un check sulla durata temporale passata per parametro.
     *
     * @param durata parametro di confronto
     * @param datetmp tempo di inizio in millisecondi per poter fare il confronto
     * @return un boolean che vale true se l'intevallo di tempo tra datetmp passato per parametro e l'istante in cui
     * viene invocato il metodo è maggiore della durata temporale in millisecondi passata per parametro; vale
     * false in caso contrario.
     */
    private boolean checktimer(int durata, Date datetmp) {
        Date date2 = new Date();
        return date2.getTime() - datetmp.getTime() > durata;
    }

    /**
     * il metodo permette di eliminare il giocatore che ha perso tutte le vite.
     *
     * @param loser il giocatore in questione
     */

    @Override
    protected void deletePlayer(Player loser) {
        int index=players.indexOf(loser);
        server.getThreadsIn().get(index).setDeletable(true);
        players.remove(loser);
        paddles.remove(index);
        numeroPlayer--;
        if(numeroPlayer >= 1) {
        gameHolder = players.get(0);
        }
    }
}
