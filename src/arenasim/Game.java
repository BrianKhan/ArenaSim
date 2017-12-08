/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arenasim;

/**
 *
 * @author Brian
 */
public class Game {



    public Game() {

    }

    public static Player determineWinner(Player first, Player second) {
        int probFirst = first.tier + 50;
        int probSecond = second.tier + 50;
        double probFirstWeight = probFirst * .01;
        double probSecondWeight = probSecond * .01;
        if (probFirst == probSecond) {
            probFirstWeight = probSecondWeight = .5;
        } else {
            probFirstWeight = probFirstWeight*(1-probSecondWeight)/(probFirstWeight*(1-probSecondWeight)+probSecondWeight*(1-probFirstWeight));
            probSecondWeight = 1-probFirstWeight;
        }
        if(Math.random() <probFirstWeight) {
            //first wins
            //System.out.println("player tier " +first.tier+ " wins");
            return first;
        }
        //System.out.println("player tier " +second.tier+ " wins");
        return second;
    }
}
