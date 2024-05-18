package com.github.moinmarcell.backend.appuser;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AppUserService {
    private final AppUserRepository appUserRepository;

    private static final List<String> BAD_USERNAMES = List.of(
            "admin",
            "root",
            "administrator",
            "superuser",
            "arschloch",
            "schwarzkopf",
            "arsch",
            "schwarz",
            "penis",
            "vagina",
            "nigger",
            "cock",
            "dick",
            "pussy",
            "whore",
            "slut",
            "cunt",
            "fuck",
            "shit",
            "ass",
            "wichser",
            "wichse",
            "wichs",
            "penner"
    );

    public AppUser updateUsername(String id, String username) {
        AppUser appUser = getById(id);
        appUser.setUsername(username);
        appUserRepository.save(appUser);
        return appUser;
    }

    public AppUser getByGoogleId(String googleId) {
        return appUserRepository
                .findByGoogleId(googleId)
                .orElseThrow(() -> new NoSuchElementException("User with googleId " + googleId + " not found"));
    }

    public boolean existsByGoogleId(String googleId) {
        return appUserRepository.existsByGoogleId(googleId);
    }

    public boolean existsByUsername(String username) {
        if (username == null) {
            return false;
        }
        if (username.isBlank()) {
            return false;
        }
        if (username.length() < 3 || username.length() > 20) {
            return false;
        }
        if (username.contains(" ")) {
            return false;
        }
        for (String badUsername : BAD_USERNAMES) {
            String lowerCaseBadUsername = badUsername.toLowerCase();
            boolean containsBadUsername = username.toLowerCase().contains(lowerCaseBadUsername);
            if (containsBadUsername) {
                return false;
            }
        }

        return !appUserRepository.existsByUsernameEqualsIgnoreCase(username);
    }

    public void create(AppUser appUser) {
        appUserRepository.save(appUser);
    }

    public void delete(String id) {
        appUserRepository.deleteById(id);
    }

    private AppUser getById(String id) {
        return appUserRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("User with id " + id + " not found"));
    }
}
