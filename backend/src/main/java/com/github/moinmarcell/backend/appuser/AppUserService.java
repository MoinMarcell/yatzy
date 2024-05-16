package com.github.moinmarcell.backend.appuser;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AppUserService {
    private final AppUserRepository appUserRepository;

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
        return appUserRepository.existsByUsernameEqualsIgnoreCase(username);
    }

    public void create(AppUser appUser) {
        appUserRepository.save(appUser);
    }

    private AppUser getById(String id) {
        return appUserRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("User with id " + id + " not found"));
    }
}
