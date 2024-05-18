package com.github.moinmarcell.backend.appuser;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AppUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AppUserRepository appUserRepository;

    @Test
    void updateUsername_expectStatusOkAndReturnUpdatedUser() throws Exception {
        AppUser existingUser = new AppUser("1", "1223", null, "url");
        appUserRepository.save(existingUser);

        AppUser expected = new AppUser("1", "1223", "marcell", "url");

        mockMvc.perform(put("/api/users/1/marcell")
                        .with(oauth2Login().attributes(attribute -> attribute.put("sub", "1223"))))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }

    @Test
    @WithMockUser
    void checkUsername_expectStatusOkAndTrue() throws Exception {
        mockMvc.perform(get("/api/users/check-username/marcell"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    @WithMockUser
    void checkUsername_expectStatusOkAndFalse() throws Exception {
        AppUser appUser = new AppUser("1", "1223", "marcell", "url");
        appUserRepository.save(appUser);

        mockMvc.perform(get("/api/users/check-username/marcell"))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
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
    @WithMockUser
    void checkUsername_expectStatusOkAndFalseWhenUsernameIsBad(String username) throws Exception {
        AppUser appUser = new AppUser("1", "1223", null, "url");
        appUserRepository.save(appUser);

        mockMvc.perform(get("/api/users/check-username/" + username))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }

    @Test
    void delete_expectStatusOk() throws Exception {
        AppUser appUser = new AppUser("1", "1223", "username", "url");
        appUserRepository.save(appUser);

        mockMvc.perform(delete("/api/users/1")
                        .with(oauth2Login().attributes(attribute -> attribute.put("sub", "1223"))))
                .andExpect(status().isNoContent());
    }
}