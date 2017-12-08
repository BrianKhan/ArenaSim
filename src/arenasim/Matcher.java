/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arenasim;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Brian
 */
public class Matcher {

    PlayerManager manager;

    public Matcher() {
        this.manager = PlayerManager.get();
    }

    public void matchGame() {
        // remember to remove players from this variable once they're not in game.
        ArrayList<Player> playersInGame = manager.getPlayersInGame();
        if (playersInGame.size() == 0) {
            return;
        }
        if (playersInGame.size() == 1) {
            playersInGame.get(0).inGame = false;
            playersInGame.remove(0);
            return;
        }
        matchRound();

        //logic
        matchGame();

    }

    public void matchRound() {
        ArrayList<Player> playersToMatch = manager.getPlayersAvailable();
        // returns 0 to size-1(max index)
        Random rand = new Random();
        if (playersToMatch.size() == 0 || playersToMatch.size() == 1) {
            for (int i = 0; i < manager.getPlayersInGame().size(); i++) {
                manager.getPlayersInGame().get(i).fight();
            }
            return;
        }
        //get 2 available players and match them
        boolean push = false;
        Player topush = null;
        //System.out.println("finding cohort");
        for (int i = -2; i < 12; i++) {
            ArrayList<Player> cohort = manager.getPlayersAvailableAtScore(i);
            if(push) {
                cohort.add(topush);
                push = false;
            }
            while (cohort.size() > 1) {
                //remove from cohort?
                int indexFirst = rand.nextInt(cohort.size());
                Player tempFirst = cohort.get(indexFirst);
                cohort.remove(tempFirst);
                int indexSecond = rand.nextInt(cohort.size());
                Player tempSecond = cohort.get(indexSecond);
                cohort.remove(tempSecond);
                tempFirst.prepareFight(tempSecond);
            }
            if(cohort.size() ==1) {
                push = true;
                topush = cohort.get(0);
            }
        }
        //System.out.println("found cohort");
        /* int indexFirst = rand.nextInt(playersToMatch.size());
        Player tempFirst = playersToMatch.get(indexFirst);
        playersToMatch.remove(tempFirst);
        int indexSecond = rand.nextInt(playersToMatch.size());
        Player tempSecond = playersToMatch.get(indexSecond);
        playersToMatch.add(tempFirst);
        tempFirst.prepareFight(tempSecond); */

        matchRound();
    }


    public void logGame() {
        for (int i = 0; i < manager.getPlayers().size(); i++) {
            manager.getPlayers().get(i).logGame();
        }
    }

}
