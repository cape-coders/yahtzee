package com.starmachine.yahtzee;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Game {
    private int currentRoll = 0;
    private int currentRound = 1;
    private final RandomRoll randomRoller;
    private final List<Integer> currentDice = new ArrayList<>(List.of(1,1,1,1,1));
    private final List<Integer> lockedDiceIndexes = new ArrayList<>();
    private Scorecard currentScorecard = new Scorecard();
    
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

    public Scorecard.ReadScorecard getScorecard() {
        return currentScorecard.getReadScorecard();
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
        switch (scoringCategory) {
            case ONES:
                currentScorecard.ones = scoreDieValue(1);
                break;
            case TWOS:
                currentScorecard.twos = scoreDieValue(2);
                break;
            case THREES:
                currentScorecard.threes = scoreDieValue(3);
                break;
            case FOURS:
                currentScorecard.fours = scoreDieValue(4);
                break;
            case FIVES:
                currentScorecard.fives = scoreDieValue(5);
                break;
            case SIXES:
                currentScorecard.sixes = scoreDieValue(6);
                break;
            case YAHTZEE:
                if (currentDice.stream().allMatch(x -> Objects.equals(x, currentDice.get(0)))) {
                    currentScorecard.yahtzee = 50;
                } else {
                    currentScorecard.yahtzee = 0;
                }
            default:
                break;
        }
    }


    void toggleDieLockedState(Integer index) {
        if (lockedDiceIndexes.contains(index)) {
            lockedDiceIndexes.remove(index);
            return;
        }
        lockedDiceIndexes.add(index);
    }


    private Integer scoreDieValue(int dieValue) {
        Integer accumulator = 0;
        for (Integer number : currentDice) {
            if (number == dieValue) {
                accumulator += dieValue;
            }
        }
        return accumulator;
    }
}
