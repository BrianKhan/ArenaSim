/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arenasim;

import java.util.ArrayList;

/**
 *
 * @author Brian
 */
public class Player {

    // @tier - int value up to 50, higher is better. win% = 50+tier. tier 10 win% = 60%
    int tier;
    ArrayList<ArenaRun> gameHistory;
    PlayerManager manager;
    //each player must have a tier
    // and a game history
    public Player(int tier) {
        this.tier = tier;
        gameHistory = new ArrayList<ArenaRun>();
        manager = PlayerManager.get();
    }
    int wins = 0;
    int losses = 0;
    boolean inGame = true;
    boolean prepared = false;
    Player toFight = null;

    // prepare a fight and set a target
    public void prepareFight(Player second) {
        this.prepared = true;
        second.prepared = true;
        this.toFight = second;
        manager.getPlayersAvailable().remove(this);
        manager.getPlayersAvailable().remove(second);
    }

    // both players fight, win or lose and/or leave the game, 
    public void fight() {
        Player second = toFight;
        if (second != null) {
            Player winner = Game.determineWinner(this, second);
            if (winner == this) {
                this.wonGame();
                second.lostGame();
            } else {
                this.lostGame();
                second.wonGame();
            }

            second.prepared = false;
            prepared = false;
            this.toFight = null;
            second.toFight=null;
            if(this.inGame()) {
                manager.getPlayersAvailable().add(this);
            }
            if(second.inGame()) {
                manager.getPlayersAvailable().add(second);
            }
        }
        

    }
    public void logGame() {
        ArenaRun temp = new ArenaRun(this,wins,losses);
        gameHistory.add(temp);
    }

    public void wonGame() {
        wins += 1;
        if (wins > 11) {
            inGame = false;
            manager.getPlayersInGame().remove(this);
            manager.getPlayersAvailable().remove(this);
        }
    }

    public void lostGame() {
        losses += 1;
        if (losses > 2) {
            inGame = false;
            manager.getPlayersInGame().remove(this);
            manager.getPlayersAvailable().remove(this);
        }
    }

    public boolean inGame() {
        return inGame;
    }

    public boolean isPrepared() {
        return prepared;
    }
    
    public void reset() {
        inGame = true;
        prepared = false;
        wins = 0;
        losses = 0;
        toFight = null;
        //gameHistory.clear();
    }
}
