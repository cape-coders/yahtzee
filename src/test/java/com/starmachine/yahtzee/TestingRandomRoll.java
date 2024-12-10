package com.starmachine.yahtzee;

import java.util.ArrayList;
import java.util.List;

public class TestingRandomRoll implements RandomRoll {
    

    private List<Integer> numbersToRoll;
    
    public TestingRandomRoll(List<Integer> numbersToRoll) {
        this.numbersToRoll = new ArrayList<>(numbersToRoll);
    }

    @Override
    public Integer roll() {
        if (numbersToRoll.isEmpty()) {
            return 1;
        }
        return numbersToRoll.remove(0);
    }

    public void setNumbersToRoll(List<Integer> numbersToRoll) {
        this.numbersToRoll = new ArrayList<>(numbersToRoll);
    }    
}
