/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arenasim;

import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 * Singleton
 */
public class PlayerManager {
    //HashSet<Player> set = new HashSet<Player>();
    ArrayList<Player> players;
    ArrayList<Player> playersInGame;
    ArrayList<Player> playersAvailable;
    private static PlayerManager self = new PlayerManager();

    public PlayerManager() {
        players = new ArrayList<Player>();
        playersInGame = new ArrayList<Player>();
        playersAvailable = new ArrayList<Player>();
    }

    public static PlayerManager get() {
        return self;
    }

    //player can only be created through a player manager
    public void addPlayer(int tier, int count) {
        for (int i = 0; i < count; i++) {
            Player temp = new Player(tier);
            players.add(temp);
        }
    }

    public void resetPlayers() {
        for (Player item : players) {
            item.reset();
        }
        playersInGame.clear();
        playersAvailable.clear();
    }
    

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public ArrayList<Player> getPlayersInGame() {

        return playersInGame;
    }

    public ArrayList<Player> calculatePlayersInGame() {
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).inGame()) {
                playersInGame.add(players.get(i));
            }
        }
        return playersInGame;
    }

    public ArrayList<Player> getPlayersAvailable() {

        return playersAvailable;
    }

    public ArrayList<Player> calculatePlayersAvailable() {

        for (int i = 0; i < players.size(); i++) {
            if (!players.get(i).isPrepared()) {
                if (players.get(i).inGame()) {
                    playersAvailable.add(players.get(i));
                }

            }
        }
        return playersAvailable;
    }

    
    public ArrayList<Player> getPlayersAvailableAtScore(int score) {
        ArrayList<Player> cohort = new ArrayList<Player>();
        for (int i = 0; i < players.size(); i++) {
            if (!players.get(i).isPrepared()) {
                if (players.get(i).inGame()) {
                    if (players.get(i).wins - players.get(i).losses == score) {
                        cohort.add(players.get(i));
                    }
                }
            }
        }
        return cohort;
    }
     
/*
    public ArrayList<Player> getPlayersAvailableAtScore(int score) {
        ArrayList<Player> cohort = new ArrayList<Player>(playersAvailable);
        for (int i = 0; i < cohort.size(); i++) {
            Player current = cohort.get(i);
            if (!(current.wins - current.losses == score)) {
                cohort.remove(current);
            } else if (current.isPrepared() || !current.inGame()) {
                cohort.remove(current);
            }
        }
        return cohort;
    }
*/
}
