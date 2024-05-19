package com.github.moinmarcell.backend.game;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "games")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Game {
    private String id;
    private String appUserId;
    private GameSheet gameSheet;
    private LocalDateTime createdAt;
}
