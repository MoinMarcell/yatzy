package com.github.moinmarcell.backend.appuser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;

import java.util.NoSuchElementException;
import java.util.Optional;

class AppUserServiceTest {

    private final AppUserRepository appUserRepository = Mockito.mock(AppUserRepository.class);
    private final AppUserService appUserService = new AppUserService(appUserRepository);

    @Test
    void updateUsername() {
        AppUser expected = new AppUser("1", "1223", null, "url");
        Mockito.when(appUserRepository.findById("1")).thenReturn(Optional.of(expected));

        expected.setUsername("newUsername");
        Mockito.when(appUserRepository.save(expected)).thenReturn(expected);

        AppUser actual = appUserService.updateUsername("1", "newUsername");

        Mockito.verify(appUserRepository).save(expected);
        Mockito.verify(appUserRepository).findById("1");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void updateUsername_throwsException() {
        Assertions.assertThrows(NoSuchElementException.class, () -> appUserService.updateUsername("1", "newUsername"));
    }

    @Test
    void getByGoogleId() {
        AppUser expected = new AppUser("1", "1223", "username", "url");
        Mockito.when(appUserRepository.findByGoogleId("1223")).thenReturn(Optional.of(expected));

        AppUser actual = appUserService.getByGoogleId("1223");

        Mockito.verify(appUserRepository).findByGoogleId("1223");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getByGoogleId_throwsException() {
        Assertions.assertThrows(NoSuchElementException.class, () -> appUserService.getByGoogleId("1223"));
    }

    @Test
    void existsByGoogleId() {
        Mockito.when(appUserRepository.existsByGoogleId("1223")).thenReturn(true);

        Assertions.assertTrue(appUserService.existsByGoogleId("1223"));
    }

    @Test
    void existsByGoogleId_returnsFalse() {
        Assertions.assertFalse(appUserService.existsByGoogleId("1223"));
    }

    @Test
    void existsByUsername_returnFalse_whenUsernameIsNull() {
        Assertions.assertFalse(appUserService.existsByUsername(null));
    }

    @Test
    void existsByUsername_returnFalse_whenUsernameIsBlank() {
        Assertions.assertFalse(appUserService.existsByUsername(""));
    }

    @Test
    void existsByUsername_returnFalse_whenUsernameLengthIsLessThan3() {
        Assertions.assertFalse(appUserService.existsByUsername("a"));
    }

    @Test
    void existsByUsername_returnFalse_whenUsernameLengthIsGreaterThan20() {
        Assertions.assertFalse(appUserService.existsByUsername("1234567891011121314151617181920"));
    }

    @Test
    void existsByUsername_returnFalse_whenUsernameContainsSpaces() {
        Assertions.assertFalse(appUserService.existsByUsername("username with spaces"));
    }

    @ParameterizedTest
    @CsvSource({
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
    })
    void existsByUsername_returnFalse_whenUsernameIsInList(String username) {
        Assertions.assertFalse(appUserService.existsByUsername(username));
    }

    @Test
    void existsByUsername_returnTrue_whenUsernameIsNotInList() {
        Mockito.when(appUserRepository.existsByUsernameEqualsIgnoreCase("username")).thenReturn(false);
        Assertions.assertTrue(appUserService.existsByUsername("username"));
    }

    @Test
    void existsByUsername_returnFalse_whenUsernameIsInList() {
        Mockito.when(appUserRepository.existsByUsernameEqualsIgnoreCase("username")).thenReturn(true);
        Assertions.assertFalse(appUserService.existsByUsername("username"));
    }

    @Test
    void create() {
        Mockito.when(appUserRepository.save(Mockito.any())).thenReturn(Mockito.any());

        AppUser appUser = new AppUser("1", "1223", "username", "url");
        appUserService.create(appUser);

        Mockito.verify(appUserRepository).save(appUser);
    }

    @Test
    void delete() {
        Mockito.doNothing().when(appUserRepository).deleteById("1");

        appUserService.delete("1");

        Mockito.verify(appUserRepository).deleteById("1");
    }
}