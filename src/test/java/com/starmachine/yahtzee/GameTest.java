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






}



