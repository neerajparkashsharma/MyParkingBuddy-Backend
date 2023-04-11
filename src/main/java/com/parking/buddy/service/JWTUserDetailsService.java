package com.parking.buddy.service;


import com.parking.buddy.entity.User;
import com.parking.buddy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;

@Service
public class JWTUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByEmailAddress(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(user.getEmailAddress(), user.getPassword(),true,true,true,true, Collections.singleton(getAuthority(user)));
    }
    private SimpleGrantedAuthority getAuthority(User user) {
        if (user.getRole() == null) {
            return new SimpleGrantedAuthority("ROLE_USER");
        } else {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().toString());
            return authority;
        }
    }

    public User save(User user) {
        try {
            User newUser = new User();
            newUser.setName(user.getName());
            newUser.setPhoneNumber(user.getPhoneNumber());
            newUser.setEmailAddress(user.getEmailAddress());
            newUser.setRole(user.getRole());
            newUser.setCreatedDate(new Date());
            newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
            newUser.setCreatedBy(Long.parseLong("0"));
            newUser.setUpdatedBy(user.getUpdatedBy());
            newUser.setUpdatedDate(user.getUpdatedDate());
            newUser.setActive(true);
            return userRepo.save(newUser);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save user: " + e.getMessage());
        }
    }

}

