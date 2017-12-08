/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arenasim;

import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Brian
 */
public class ArenaSim {

    /**
     * main class for ArenaSim Creates a new Arena game with the number of
     * players and their tier Runs x amount of games
     */
    public static void main(String[] args) {
        // TODO code application logic here
        ArenaGame first = new ArenaGame();
        buildPlayers(first, 100000);
        runGame(first, 20, 6);
        runGame(first, 30, 6);

    }

    public static void buildPlayers(ArenaGame first, int sampleSize) {
        // we're going to build the "average" hearthstone arena playerbase based on a normal curve
        // n =~ 1,000,000
        // 
        // more nuanced - high population of low skill players but they do'nt play 30 games
        int n = sampleSize;
        // converges to cumulative sum of 1.359
        //double total = 0;
        double push = 0;
        int players = 0;
        for (double i = -5; i < 4.9; i += .1) {
            //total sum of each amount is 10
            // but each amount can be so low that we need to add the results
            double amount = Math.pow(Math.E, ((-.5) * Math.pow(((0 - i) / 1), 2))) / (1 * Math.sqrt(2 * Math.PI))
            );
            // get a % out of 100
            int tier = (int) (i * 10);
            //since we converge to a sum of 10, div by 10 to get a percentage
            amount = amount / 9.99;
            // find our amount of players at the current tier
            amount = n * amount;
            if ((amount + push) < 1) {
                push += amount;
                amount = 0;
            } //total+=amount;
            else {
                first.addPlayers(tier, (int) (amount + push));
                players+=(int)(amount+push);
                System.out.println("adding tier " + tier + " with #: " + (int)(amount+push));
                push = (amount+push)-(int)(amount+push);
                
            }
            
        }
        System.out.println(players + " players");

    }

    public static void runGame(ArenaGame first, int countGames, int countMonths) {
        for (int j = 0; j < countMonths; j++) {
            for (int i = 0; i < countGames; i++) {
                System.out.println("Starting Run " + i);
                first.runGame();
                System.out.println("Run " + i + " finished");
                System.out.println("Printing Run " + i);

            }
            try {

                first.toCSV("" + countGames + "_" + j);
                first.resetRun();
                // for(int i = 0; i < 99; i++) {
                //  first.fight(second);
                // }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ArenaSim.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
