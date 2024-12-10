package com.starmachine.yahtzee;

import java.util.Random;

public class RandomDiceRoll implements RandomRoll {
    private final Random random = new Random();

    @Override
    public Integer roll() {
        return random.nextInt(6) + 1; 
    }
    
}
