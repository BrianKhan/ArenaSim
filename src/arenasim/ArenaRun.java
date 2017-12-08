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
public class ArenaRun {
    int wins;
    int losses;
    Player id;
    public ArenaRun(Player individual, int wins, int losses) {
        id = individual;
        this.wins= wins;
        this.losses=losses;
    }
}
