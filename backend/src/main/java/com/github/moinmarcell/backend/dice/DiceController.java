package com.github.moinmarcell.backend.dice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/dices")
public class DiceController {
    @GetMapping("/roll/{amount}")
    public ResponseEntity<Map<String, Integer>> roll(@PathVariable("amount") int amount) {
        if (amount < 1 || amount > 6) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Amount must be greater than 0 and less than 7");
        }

        Map<String, Integer> dices = new HashMap<>();
        for (int i = 1; i <= amount; i++) {
            dices.put("dice " + i, (int) (Math.random() * 6) + 1);
        }
        int totalOnes = (int) dices.values().stream().filter(i -> i == 1).count();
        if (totalOnes >= 1) {
            dices.put("totalOnes", totalOnes);
        }

        int totalTwos = (int) dices.values().stream().filter(i -> i == 2).count();
        if (totalTwos >= 2) {
            dices.put("totalTwos", totalTwos);
        }

        int totalThrees = (int) dices.values().stream().filter(i -> i == 3).count();
        if (totalThrees >= 3) {
            dices.put("totalThrees", totalThrees);
        }

        int totalFours = (int) dices.values().stream().filter(i -> i == 4).count();
        if (totalFours >= 4) {
            dices.put("totalFours", totalFours);
        }

        int totalFives = (int) dices.values().stream().filter(i -> i == 5).count();
        if (totalFives >= 5) {
            dices.put("totalFives", totalFives);
        }

        int totalSixes = (int) dices.values().stream().filter(i -> i == 6).count();
        if (totalSixes >= 6) {
            dices.put("totalSixes", totalSixes);
        }

        return ResponseEntity.ok(dices);
    }
}
