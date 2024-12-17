package com.starmachine.yahtzee;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameTest {

    private Game game;
    private TestingRandomRoll randomRoller;

    
    @BeforeEach
    public void setup() {
        randomRoller = new TestingRandomRoll(List.of(6,6,6,6,6));
        game = new Game(randomRoller);
    }

    @Test
    public void whenGameStarts_roundShouldbeSetTo1() {
        assertEquals(1, game.getRound());
    }

    @Test
    public void whenGameStarts_rollsShouldBeSetTo0() {
        assertEquals(0, game.getRolls());
    }

    @Test
    public void whenPlayerRollsDice_rollsShouldIncrementBy1() {
        game.roll();
        assertEquals(1, game.getRolls());
    }

    @Test
    public void whenPlayerRolls3Times_cantRollAgainUntilScores(){
        game.roll();
        game.roll();
        game.roll();
        RuntimeException assertThrows = assertThrows(RuntimeException.class, () -> {
            game.roll();
        });
        assertEquals("you have to score before you roll again", assertThrows.getMessage());
    }

    @Test
    public void whenPlayerRolls_currentDiceStateShouldBeenShown() {
        game.roll();
        List<Integer> diceState = game.getCurrentDiceState();
        assertEquals(5, diceState.size());
        assertTrue(diceState.stream().allMatch(x -> x > 0 && x < 7));
    }

    @Test
    public void rollingShouldGenerateRandomNumbers() {
        randomRoller.setNumbersToRoll(List.of(1,2,1,4,1));
        game.roll();
        assertEquals(List.of(1,2,1,4,1), game.getCurrentDiceState());
    }

    @Test
    public void scoringShouldIncrementRound() {
        game.roll();
        game.score(ScoringCategory.CHANCE);
        assertEquals(2, game.getRound());
    }

    @Test
    public void scoringShouldResetRollsTo0() {
        game.roll();
        game.score(ScoringCategory.CHANCE);
        assertEquals(0, game.getRolls());
    }

    @Test
    public void dieThatUserLocks_shouldNotBeRerolled() {
        randomRoller.setNumbersToRoll(List.of(1,2,3,4,5));
        game.roll();
        game.toggleDieLockedState(0);
        randomRoller.setNumbersToRoll(List.of(6,6,6,6,6));
        game.roll();
        assertEquals(List.of(1,6,6,6,6), game.getCurrentDiceState());
    }

    @Test
    public void dieThatUserLocksCanBeUnlocked() {
        randomRoller.setNumbersToRoll(List.of(1,2,3,4,5));
        game.roll();
        game.toggleDieLockedState(0);
        game.toggleDieLockedState(1);
        game.toggleDieLockedState(2);
        game.toggleDieLockedState(3);
        randomRoller.setNumbersToRoll(List.of(6,6,6,6,6));
        game.roll();
        assertEquals(List.of(1,2,3,4,6), game.getCurrentDiceState());
        game.toggleDieLockedState(0);
        game.roll();
        assertEquals(List.of(6,2,3,4,6), game.getCurrentDiceState());

    }

    @Test
    public void scoringOnesShouldSumNumberOfDieWithOne() {
        setupScoringTest(List.of(1,1,1,2,3), ScoringCategory.ONES);
        assertEquals(3, game.getScorecard().ones());
    }

    @Test
    public void scoringOnesShouldScoreZeroIfNoOnes() {
        setupScoringTest(List.of(3,3,4,5,2), ScoringCategory.ONES);
        assertEquals(0, game.getScorecard().ones());
    }

    @Test
    public void scoringTwosShouldSumNumberOfDieWithTwo() {
        setupScoringTest(List.of(2,2,4,5,6), ScoringCategory.TWOS);
        assertEquals(4, game.getScorecard().twos());
    }

    @Test
    public void scoringTwosShouldScoreZeroIfNoTwos() {
        setupScoringTest(List.of(3,3,4,5,6), ScoringCategory.TWOS);
        assertEquals(0, game.getScorecard().twos());
    }

    @Test
    public void scoringThreesShouldSumThreesOnDice() {
        setupScoringTest(List.of(3,3,3,5,6), ScoringCategory.THREES);
        assertEquals(9, game.getScorecard().threes());
    }

    
    @Test
    public void scoringThreesShouldScoreZeroIfNoThrees() {
        setupScoringTest(List.of(2,1,2,5,6), ScoringCategory.THREES);
        assertEquals(0, game.getScorecard().threes());
    }

    @Test
    public void scoringAYahtzeeShouldScore50PointsWhenAllDiceAreSameNumber() {
        setupScoringTest(List.of(6,6,6,6,6), ScoringCategory.YAHTZEE);
        assertEquals(50, game.getScorecard().yahtzee());
    }

    @Test
    public void scoringAYahtzeeShouldScore0PointsWhenAllDiceAreNotSameNumber() {
        setupScoringTest(List.of(6,6,6,6,1), ScoringCategory.YAHTZEE);
        assertEquals(0, game.getScorecard().yahtzee());
    }

    @Test
    public void shouldStartGameWithNoYahtzeeScore() {
        
        assertEquals(null, game.getScorecard().yahtzee());
    }


    private void setupScoringTest(List<Integer> dice, ScoringCategory scoringCategory) {
        randomRoller.setNumbersToRoll(dice);
        game.roll();
        game.score(scoringCategory);
    }







}



