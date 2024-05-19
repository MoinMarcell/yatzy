package com.github.moinmarcell.backend.config;

import com.github.moinmarcell.backend.appuser.AppUser;
import com.github.moinmarcell.backend.appuser.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Value("${app.url}")
    private String appUrl;

    private final AppUserService appUserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .exceptionHandling(c -> c.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .authorizeHttpRequests(c -> c
                        .requestMatchers("/api/dices/roll/**").permitAll()
                        .anyRequest().permitAll()
                )
                .oauth2Login(c -> c.successHandler((request, response, authentication) -> {
                    var principal = (OAuth2User) authentication.getPrincipal();
                    String googleId = principal.getAttributes().get("sub").toString();
                    AppUser appUser = appUserService.getByGoogleId(googleId);

                    if (appUser.getUsername() == null || appUser.getUsername().isBlank()) {
                        response.sendRedirect(getRegisterUrl());
                    } else {
                        response.sendRedirect(getDefaultSuccessUrl());
                    }

                }))
                .logout(c -> c.logoutSuccessUrl(getLogoutSuccessUrl()));

        return http.build();
    }

    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService() {
        DefaultOAuth2UserService userService = new DefaultOAuth2UserService();

        return request -> {
            OAuth2User user = userService.loadUser(request);

            if (!appUserService.existsByGoogleId(user.getAttributes().get("sub").toString())) {
                AppUser newUser = new AppUser(
                        null,
                        user.getAttributes().get("sub").toString(),
                        null,
                        user.getAttributes().get("picture").toString()
                );
                appUserService.create(newUser);
                return user;
            }

            return user;
        };
    }

    private String getRegisterUrl() {
        final boolean hasAppUrlSlash = appUrl.charAt(appUrl.length() - 1) == '/';
        return hasAppUrlSlash ? appUrl + "register" : appUrl + "/register";
    }

    private String getDefaultSuccessUrl() {
        final boolean hasAppUrlSlash = appUrl.charAt(appUrl.length() - 1) == '/';
        return hasAppUrlSlash ? appUrl : appUrl + "/";
    }

    private String getLogoutSuccessUrl() {
        final boolean hasAppUrlSlash = appUrl.charAt(appUrl.length() - 1) == '/';
        return hasAppUrlSlash ? appUrl + "login" : appUrl + "/login";
    }

}
