package com.github.moinmarcell.backend.auth;

import com.github.moinmarcell.backend.appuser.AppUser;
import com.github.moinmarcell.backend.appuser.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AppUserService appUserService;

    @GetMapping("/me")
    public AppUser getMe(@AuthenticationPrincipal OAuth2User user) {
        String googleId = user.getAttributes().get("sub").toString();
        return appUserService.getByGoogleId(googleId);
    }

}
