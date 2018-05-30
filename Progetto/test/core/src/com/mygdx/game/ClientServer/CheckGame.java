/*
 * DA FARE:
 *
 * Non fare mille metodi checkwin() checlose(), checkpause() ecc.
 * O se si fanno, fare in modo di passare pochi parametri, o comunque trovare una soluzione
 * che renda utile questa classe
 *
 *
 * Valutare l'uso di ereditarietà e polimorfismo
 *
 *
 *
 * */

package com.mygdx.game.ClientServer;
import com.mygdx.game.Levels.GestoreLivelli;
import help.GameState;


public class CheckGame {


    public static void checkWin(int matEliminati, GestoreLivelli gestoreLivelli, int livelloCorrente, boolean isFinished, GameState gameState) {
        if (matEliminati == gestoreLivelli.getLivello(livelloCorrente - 1).getnMatMorbidi()) {
            gameState = GameState.YOU_WON;

            livelloCorrente++;
            if (livelloCorrente > gestoreLivelli.getNumeroLivelli()) {
                isFinished = true;
            }
        }
    }
}
