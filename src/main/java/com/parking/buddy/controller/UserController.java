package com.parking.buddy.controller;

import com.parking.buddy.entity.User;
import com.parking.buddy.exception.InvalidRequestException;
import com.parking.buddy.exception.ResourceAlreadyExistsException;
import com.parking.buddy.exception.ResourceNotFoundException;
import com.parking.buddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Response;
import java.util.*;

@RestController
public class UserController {


    @Autowired
    private UserService userService;
    @GetMapping("/users")
    public List<User> AllUsers() {
        return userService.getUsers();
    }
    @PostMapping("/update/location/{location}/{id}")
    public ResponseEntity<?> saveLocationRadius(@PathVariable String location, @PathVariable String id) {
        try {
            User u = userService.getUserById(Long.parseLong(id));
            u.setLocationDistance(Double.parseDouble(location));
            userService.saveUser(u);
            return ResponseEntity.ok("Success");
        } catch (Exception e) {
            System.out.println("exception");
            return ResponseEntity.badRequest().body(e.toString());
        }
    }


    @PutMapping("/users")
    public ResponseEntity<?> updateUserInfo(@RequestBody  User user){
        return  userService.updateUser(user);
    }



    @GetMapping("/users/{email}")
    public User UserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }

    @GetMapping("/users/id/{id}")
    public User UserById(@PathVariable String id) {
        return userService.getUserById(Long.parseLong(id));
    }

    @PostMapping("/user")
    public String saveUser(@RequestBody User user) {
        try {
            if(userService.getUserByEmail(user.getEmailAddress()) != null) {
                throw new ResourceAlreadyExistsException("User with email address " + user.getEmailAddress() + " already exists.");
            }
            return userService.saveUser(user);
        } catch (Exception e) {
            return e.toString();
        }
    }


    @PostMapping("/login")
    public String Login(@RequestBody User user) {
        return userService.Login(user.getEmailAddress(), user.getPassword());
    }
    @PostMapping("/location-setting/{location}/{id}")
    public ResponseEntity<String> updateLocation(@PathVariable String location, @PathVariable String id) {
        try {
            User u = userService.getUserById(Long.parseLong(id));
            if (u == null) {
                throw new ResourceNotFoundException("User not found with ID " + id);
            }
            u.setLocationDistance(Double.parseDouble(location));
            u.setUpdatedDate(new Date());
            u.setUpdatedBy(u.getId());
            userService.saveUser(u);
            return new ResponseEntity<>("Success", HttpStatus.OK);
        } catch (NumberFormatException e) {
            throw new InvalidRequestException("Invalid ID or location value provided");
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (ResourceAlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

