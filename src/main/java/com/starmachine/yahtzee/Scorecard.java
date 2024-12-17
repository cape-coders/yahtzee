package com.starmachine.yahtzee;


public class Scorecard {
    Integer ones;
    Integer twos;
    Integer threes;
    Integer fours;
    Integer fives;
    Integer sixes;
    Integer yahtzee;

    public ReadScorecard getReadScorecard(){
        return new ReadScorecard(ones, twos, threes, fours, fives, sixes, yahtzee);
    }

    
     record ReadScorecard(
        Integer ones,
        Integer twos,
        Integer threes,
        Integer fours,
        Integer fives,
        Integer sixes,
        Integer yahtzee
    ) {}
}
