package com.parking.buddy.controller;


import com.parking.buddy.config.JwtTokenUtil;
import com.parking.buddy.entity.JwtRequest;
import com.parking.buddy.entity.JwtResponse;
import com.parking.buddy.entity.User;
import com.parking.buddy.service.JWTUserDetailsService;
import com.parking.buddy.service.RoleService;
import com.parking.buddy.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class JwtAuthenticationController {
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationController.class);
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JWTUserDetailsService userDetailsService;

    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        System.out.println("authenticate");
        try
        {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());




           final String token = jwtTokenUtil.generateToken(userDetails);

            return ResponseEntity.ok(new JwtResponse(
                    token,
                    userDetails.getUsername(),
                    userService.getUserByEmail(userDetails.getUsername()).getId(),
                    userService.getUserByEmail(userDetails.getUsername()).getRole()


            ));
        }
        catch (Exception e)
        {
            return ResponseEntity.notFound().build();
        }

    }

    @Autowired
    UserService userService;
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> saveUser(@RequestBody User user) throws Exception {

      if (userService.getUserByEmail(user.getEmailAddress()) != null) {
            return ResponseEntity.ok("User already exists");
        }

        user.setCreatedDate(new Date());
        user.setCreatedBy(Integer.toUnsignedLong(0));
        return ResponseEntity.ok(userDetailsService.save(user));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(username, password, userDetailsService.loadUserByUsername(username).getAuthorities()));

        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
