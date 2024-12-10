package com.starmachine.yahtzee;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private int currentRoll = 0;
    private int currentRound = 1;
    private final RandomRoll randomRoller;
    private final List<Integer> currentDice = new ArrayList<>(List.of(1,1,1,1,1));
    private final List<Integer> lockedDiceIndexes = new ArrayList<>();

    
    public Game(RandomRoll randomRoller) {
        this.randomRoller = randomRoller;
    }

    public Integer getRound() {
        return currentRound;
    }

    public Integer getRolls() {
        return currentRoll;
    }

    List<Integer> getCurrentDiceState() {
        return currentDice;
    }

    
    void roll() {
        currentRoll++;
        if (currentRoll > 3) {
            throw new RuntimeException("you have to score before you roll again");
        }
        for (int i = 0; i < 5; i++) {
            if(!lockedDiceIndexes.contains(i)) {
                currentDice.set(i, randomRoller.roll());
            }
        }
    } 

    void score(ScoringCategory scoringCategory) {
        currentRound++;
        currentRoll = 0;
    }

    void toggleDieLockedState(Integer index) {
        if (lockedDiceIndexes.contains(index)) {
            lockedDiceIndexes.remove(index);
            return;
        }
        lockedDiceIndexes.add(index);
    }


}
