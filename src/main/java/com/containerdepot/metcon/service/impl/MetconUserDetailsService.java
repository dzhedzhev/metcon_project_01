package com.containerdepot.metcon.service.impl;

import com.containerdepot.metcon.data.UserRepository;
import com.containerdepot.metcon.model.MetconUserDetails;
import com.containerdepot.metcon.model.entities.UserEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MetconUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public MetconUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepository
                .findByUsername(username)
                .map(MetconUserDetailsService::map)
                .orElseThrow(() -> new UsernameNotFoundException("User with username" + username + "not found!"));
    }

    private static UserDetails map(UserEntity userEntity) {
//        return User
//                .withUsername(userEntity.getUsername())
//                .password(userEntity.getPassword())
//                .authorities(List.of())/*TODO*/
//                .disabled(false)
//                .build();
        return new MetconUserDetails(
                userEntity.getUsername(),
                userEntity.getPassword(),
                List.of(), /*TODO*/
                userEntity.getFirstName(),
                userEntity.getLastName()
        );
    }
}
