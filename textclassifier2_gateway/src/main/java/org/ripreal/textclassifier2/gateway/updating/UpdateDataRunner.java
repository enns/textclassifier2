package org.ripreal.textclassifier2.gateway.updating;

import org.ripreal.textclassifier2.gateway.domain.Country;
import org.ripreal.textclassifier2.gateway.domain.RandomCity;
import org.ripreal.textclassifier2.gateway.domain.User;
import org.ripreal.textclassifier2.gateway.repository.RandomCityRepository;
import org.ripreal.textclassifier2.gateway.service.GenericService;
import org.ripreal.textclassifier2.gateway.service.impl.AppUserDetailsService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

@Configuration
@Transactional
public class UpdateDataRunner {

    private final AppUserDetailsService userService;
    private final GenericService genericService;
    private final RandomCityRepository cityRepo;

    public UpdateDataRunner(AppUserDetailsService userService, GenericService genericService, RandomCityRepository cityRepo) {
        this.userService = userService;
        this.genericService = genericService;
        this.cityRepo = cityRepo;
    }

    @Bean
    @Transactional
    public CommandLineRunner init() {
        return args -> {
            userService.changePassword("admin.admin", "jwtpass");

            User user = new User()
                .setFirstName("admin2")
                .setLastName("admin2")
                .setUsername("admin2.admin2")
                .setPassword("jwtpass");

            RandomCity city = new RandomCity();
            city.setName("TORONTO");
            city.setCountry(new Country("CANADA"));
            Long version = city.getVersion();
            cityRepo.save(city);
            Iterable<RandomCity> cities = cityRepo.findAll();
            cityRepo.save(city);
            cities = cityRepo.findAll();
            Iterable<User> users = genericService.findAllUsers();
            String t = "";

        };
    }
}