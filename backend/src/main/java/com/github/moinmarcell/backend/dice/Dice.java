package com.github.moinmarcell.backend.dice;

public class Dice {
    public static int roll() {
        return (int) (Math.random() * 6) + 1;
    }
}
