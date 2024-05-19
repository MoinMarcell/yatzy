package com.github.moinmarcell.backend.dice;

import org.springframework.stereotype.Service;

@Service
public class DiceService {

    public Dice roll(int amount) {
        if (amount < 1 || amount > 6) {
            throw new IllegalArgumentException("Amount must be greater than 0 and less than 7");
        }

        return new Dice(
                roll(),
                roll(),
                roll(),
                roll(),
                roll(),
                roll()
        );
    }

    private int roll() {
        return (int) (Math.random() * 6) + 1;
    }
}
