package org.ripreal.textclassifier2.gateway.service.impl;

import org.ripreal.textclassifier2.gateway.domain.User;
import org.ripreal.textclassifier2.gateway.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nydiarra on 06/05/17.
 */
@Component
public class AppUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(s);

        if(user == null) {
            throw new UsernameNotFoundException(String.format("The username %s doesn't exist", s));
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        });

        UserDetails userDetails = new org.springframework.security.core.userdetails.
                User(user.getUsername(), user.getPassword(), authorities);
        return userDetails;
    }

    public void encodeAllUsers(String newPassword) {
        Iterable<User> users = userRepository.findAll();
        //users.forEach(user -> user.setPassword("{bcrypt}" + passwordEncoder.encode(newPassword)));
        //users.forEach(user -> user.setPassword(passwordEncoder.encode(newPassword)));
        users.forEach(user -> {
            System.out.println(passwordEncoder.matches(newPassword, user.getPassword()));
        });
        userRepository.saveAll(users);
    }
}
