/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arenasim;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 *
 * Creates PlayerManager as well as setting up players with parameters from main
 * runs the matcher until all players have won or lost
 * prints/saves a log of wins/losses
 */
public class ArenaGame {
    PlayerManager manager;
    Matcher match;
    // every arena game needs to have a player list
    public ArenaGame() {
        this.manager = PlayerManager.get();
        this.match = new Matcher();
    }
    // function used by ArenaSim main method to add players to list
    public void addPlayers(int tier, int count) {
        manager.addPlayer(tier,count);
    }
    
    public void runGame() {
        manager.calculatePlayersAvailable();
        manager.calculatePlayersInGame();
        match.matchGame();
        match.logGame();
        manager.resetPlayers();
    }
    public void resetRun() {
        for(Player item : manager.getPlayers()) {
            item.gameHistory.clear();
        }
    }
    public void toCSV(String fileName) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(new File(fileName+".csv"));
        StringBuilder sb = new StringBuilder();
        for(Player item : manager.getPlayers()) {
            sb.append(item.hashCode());
            sb.append(',');
            sb.append(item.tier);
            sb.append(',');
            sb.append(item.gameHistory.size());
            for(ArenaRun run : item.gameHistory) {
                sb.append(",");
                sb.append(run.wins-run.losses);
                
            }
            sb.append('\n');
        }
        pw.write(sb.toString());
       
        pw.close();
    }
    
}
