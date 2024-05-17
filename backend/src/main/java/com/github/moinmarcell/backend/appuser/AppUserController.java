package com.github.moinmarcell.backend.appuser;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class AppUserController {
    private final AppUserService appUserService;

    @PutMapping("/{id}/{username}")
    public AppUser updateUsername(
            @PathVariable("id") String id,
            @PathVariable("username") String username,
            @AuthenticationPrincipal OAuth2User oauth2User
    ) {
        AppUser appUser = appUserService.getByGoogleId(oauth2User.getAttributes().get("sub").toString());

        if (!appUser.getId().equals(id)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can only update your own username");
        }

        return appUserService.updateUsername(id, username);
    }

    @GetMapping("/check-username/{username}")
    public boolean checkUsername(@PathVariable("username") String username) {
        return appUserService.existsByUsername(username);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable("id") String id,
            @AuthenticationPrincipal OAuth2User oauth2User,
            HttpSession session
    ) {
        AppUser appUser = appUserService.getByGoogleId(oauth2User.getAttributes().get("sub").toString());

        if (!appUser.getId().equals(id)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can only delete your own account");
        }

        appUserService.delete(id);
        session.invalidate();
        SecurityContextHolder.clearContext();
    }
}
