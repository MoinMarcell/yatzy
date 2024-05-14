package com.github.moinmarcell.backend.appuser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "app-users")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppUser {
    @MongoId
    private String id;
    @Indexed(unique = true)
    private String googleId;
    @Indexed(unique = true)
    private String username;
    private String avatarUrl;
}
