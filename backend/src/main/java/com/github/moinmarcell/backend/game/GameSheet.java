package com.github.moinmarcell.backend.game;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GameSheet {
    private int ones;
    private int twos;
    private int threes;
    private int fours;
    private int fives;
    private int sixes;
    private int sum;
    private int bonus;
    private int threeOfKind;
    private int fourOfKind;
    private int fullHouse;
    private int smallStreet;
    private int largeStreet;
    private int chance;
    private int yatzy;
    private int total;
}
