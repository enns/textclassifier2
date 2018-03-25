package org.ripreal.textclassifier2.gateway.updating;

import org.ripreal.textclassifier2.gateway.service.impl.AppUserDetailsService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UpdateDataRunner {

    private final AppUserDetailsService userService;

    private final PasswordEncoder passwordEncoder;

    public UpdateDataRunner(AppUserDetailsService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public CommandLineRunner init() {
        return args -> {
            userService.encodeAllUsers("jwtpass");
        };
    }
}