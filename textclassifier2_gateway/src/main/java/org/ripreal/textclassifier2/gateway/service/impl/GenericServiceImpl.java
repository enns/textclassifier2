package org.ripreal.textclassifier2.gateway.service.impl;

import org.ripreal.textclassifier2.gateway.domain.RandomCity;
import org.ripreal.textclassifier2.gateway.domain.User;
import org.ripreal.textclassifier2.gateway.repository.RandomCityRepository;
import org.ripreal.textclassifier2.gateway.repository.UserRepository;
import org.ripreal.textclassifier2.gateway.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenericServiceImpl implements GenericService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RandomCityRepository randomCityRepository;

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> findAllUsers() {
        return (List<User>)userRepository.findAll();
    }

    @Override
    public List<RandomCity> findAllRandomCities() {
        return (List<RandomCity>)randomCityRepository.findAll();
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }
}
