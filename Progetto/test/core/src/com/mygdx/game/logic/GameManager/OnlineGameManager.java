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
     * Si aggiorna 60 volte al secondo, e aggiorna le posizione degli oggetti
     * Gestisce i movimenti del player
     *
     * @see:writeMessage();
     * @see:gestisciCollisioni();
     * @see:checktimerpowerup(); Gestisce lo stato della partita
     */
    @Override
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
            gameState=GameState.WAIT;
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

    private void writeMessage() {

        System.out.println("A");
        String message = "";
        if (numeroPlayer == 0) {
            message = "Empty";
        } else {
            message += palla.getPositionBall().x + " " + palla.getPositionBall().y + "\t";
            for (Paddle paddle : paddles) {
                message += paddle.getPosition().x + " ";
            }
            message += "\t";

            for (float f : Info.getInstance().getPaddleresizex()) {
                message += f + " ";
            }
            message += "\t";

            for (Player player : players) {
                message += player.getPlayerName() + " ";
                message += player.getScore() + " ";
                message += player.getLives() + " ";
                message += "\t";
            }

            for (Brick brick : bricks) {
                message += brick.getPositionBrick().x + " " + brick.getPositionBrick().y + " ";
                message += brick.getClass().getSimpleName() + "\t";
            }
            for (PowerUp powerUp : powerUps) {
                message += powerUp.getPosition().x + " " + powerUp.getPosition().y + " ";
                message += powerUp.getClass().getSimpleName() + "\t";
            }

            String bgPath = ((FileTextureData) bg.getTextureData()).getFileHandle().name();

            message += bgPath + "\t";


            message = message.substring(0, message.length() - 1);
        }
        byte[] bytes = message.getBytes();

        server.sendMessage(bytes);

        if (numeroPlayer == 0) {
            Gdx.app.exit();
        }
    }

    private boolean checktimer(int durata, Date datetmp) {
        Date date2 = new Date();
        if (date2.getTime() - datetmp.getTime() > durata) {
            return true;
        }
        return false;
    }

    @Override
    protected void deletePlayer(Player loser) {
        int index=players.indexOf(loser);
        players.remove(loser);
        paddles.remove(index);
        numeroPlayer--;
    }
}
