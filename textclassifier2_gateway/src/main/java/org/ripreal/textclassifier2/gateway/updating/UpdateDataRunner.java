package org.ripreal.textclassifier2.gateway.updating;

import org.ripreal.textclassifier2.gateway.domain.User;
import org.ripreal.textclassifier2.gateway.service.GenericService;
import org.ripreal.textclassifier2.gateway.service.impl.AppUserDetailsService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UpdateDataRunner {

    private final AppUserDetailsService userService;
    private final GenericService genericService;


    private final PasswordEncoder passwordEncoder;

    public UpdateDataRunner(AppUserDetailsService userService, GenericService genericService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.genericService = genericService;
    }

    @Bean
    public CommandLineRunner init() {
        return args -> {
            userService.changePassword("admin.admin", "jwtpass");
            genericService.saveUser(new User()
                .setFirstName("admin2")
                .setLastName("admin2")
                .setUsername("admin2.admin2")
                .setPassword("jwtpass"));
            Iterable<User> users = genericService.findAllUsers();
            String t = "";
        };
    }
}