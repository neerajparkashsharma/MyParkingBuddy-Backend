package com.parking.buddy.service;

import com.parking.buddy.entity.Role;
import com.parking.buddy.entity.User;
import com.parking.buddy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;





    public List<User> getUsers() {
        try {
            return userRepository.findAll();
        } catch (Exception e) {
            throw e;
        }
    }

    public User getUserById(Long id) {
        try {
            return userRepository.findById(id).orElse(null);
        } catch (Exception e) {
            throw e;
        }
    }

    public User getUserByEmail(String email) {
        try {
            return userRepository.findByEmailAddress(email);
        } catch (Exception e) {
            throw e;
        }
    }

    public User getUserByPhone(String phoneNumber) {
        try {
            return userRepository.findByPhoneNumber(phoneNumber);
        } catch (Exception e) {
            throw e;
        }
    }

    public String Login(String email, String password) {
        try {
            User user = userRepository.findByEmailAddress(email);
            if (user == null) {
                return "User not found";
            }
            if (user.getPassword().equals(password)) {
                return "Login successful";
            }
            return "Wrong password";
        } catch (Exception e) {
            throw e;
        }
    }

    public String saveUser(User user) {
        try {
            user.setCreatedDate(new Date());
            userRepository.save(user);
            return "User added successfully";
        } catch (Exception e) {
            throw e;
        }
    }

    public ResponseEntity<?> updateUser(User user) {
        try {
            User existingUser = userRepository.findById(user.getId()).orElse(null);
            if (existingUser == null) {
                return  ResponseEntity.notFound().build();
            }


            existingUser.setName(user.getName());
            existingUser.setEmailAddress(user.getEmailAddress());
            existingUser.setPhoneNumber(user.getPhoneNumber());


            userRepository.save(existingUser);


            return ResponseEntity.accepted().body("User updated successfully");

        } catch (Exception e) {
                return   ResponseEntity.internalServerError().body("Error updating user");
        }
    }





}
