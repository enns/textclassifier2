package org.ripreal.textclassifier2.gateway.service;

import org.ripreal.textclassifier2.gateway.domain.RandomCity;
import org.ripreal.textclassifier2.gateway.domain.User;

import java.util.List;

/**
 * Created by nydiarra on 06/05/17.
 */
public interface GenericService {
    User findByUsername(String username);

    List<User> findAllUsers();

    List<RandomCity> findAllRandomCities();
}
