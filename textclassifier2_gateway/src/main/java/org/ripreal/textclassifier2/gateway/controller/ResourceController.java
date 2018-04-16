package org.ripreal.textclassifier2.gateway.controller;

import org.ripreal.textclassifier2.gateway.domain.Country;
import org.ripreal.textclassifier2.gateway.domain.RandomCity;
import org.ripreal.textclassifier2.gateway.domain.User;
import org.ripreal.textclassifier2.gateway.security.SecurityUtils;
import org.ripreal.textclassifier2.gateway.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by nydiarra on 06/05/17.
 */
@RestController
@RequestMapping("/springjwt")
public class ResourceController {
    @Autowired
    private GenericService userService;

    @Autowired
    private EntityManager entityManager;

    @RequestMapping(value ="/cities")
    @PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')")
    public List<RandomCity> getUser(){
        return userService.findAllRandomCities();
    }

    @RequestMapping(value ="/users", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ADMIN_USER')")
    @Transactional
    public List<User> getUsers(){
        return userService.findAllUsers();
    }
}
