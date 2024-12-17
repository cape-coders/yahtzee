package com.starmachine.yahtzee;


public class Scorecard {
    Integer ones;
    Integer twos;
    Integer threes;
    Integer yahtzee;

    public ReadScorecard getReadScorecard(){
        return new ReadScorecard(ones, twos, threes, yahtzee);
    }

    
     record ReadScorecard(
        Integer ones,
        Integer twos,
        Integer threes,
        Integer yahtzee
    ) {}
}
