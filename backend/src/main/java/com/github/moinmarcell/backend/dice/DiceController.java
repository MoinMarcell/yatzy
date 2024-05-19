package com.github.moinmarcell.backend.dice;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dices")
@RequiredArgsConstructor
public class DiceController {
    private final DiceService diceService;

    @GetMapping("/roll/{amount}")
    public Dice roll(
            @PathVariable("amount")
            int amount
    ) {
        return diceService.roll(amount);
    }
}
