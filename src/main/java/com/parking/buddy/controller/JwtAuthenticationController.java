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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class JwtAuthenticationController {
    private static final Logger LOGGER= LoggerFactory.getLogger(JwtAuthenticationController.class);
//    private final SessionRepository<?> sessionRepository;
//    private final SpringSessionBackedSessionRegistry<?> sessionRegistry;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JWTUserDetailsService userDetailsService;

    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest, HttpSession session) throws Exception {
        System.out.println("authenticate");
        try
        {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());



            session.setAttribute("username", userDetails.getUsername());
            session.setAttribute("userId", userService.getUserByEmail(userDetails.getUsername()).getId());


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

    @RequestMapping(value = "/authenticate/token", method = RequestMethod.POST)
        public ResponseEntity<?> authenticateWithToken(@RequestHeader("Authorization") String token,HttpSession session) throws Exception {
        // Remove the "Bearer " prefix from the token string
        String authToken = token.replace("Bearer ", "");

        // Validate the token
        if (jwtTokenUtil.validateToken(authToken)) {
            // Extract the username from the token
            String username = jwtTokenUtil.getUsernameFromToken(authToken);

            // Load the user details based on the username
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // Create a new authentication object
            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            // Set the authentication in the security context
            SecurityContextHolder.getContext().setAuthentication(authentication);


            session.setAttribute("username", userDetails.getUsername());
            session.setAttribute("userId", userService.getUserByEmail(userDetails.getUsername()).getId());



            return ResponseEntity.ok(new JwtResponse(
                    token,
                    userDetails.getUsername(),
                    userService.getUserByEmail(userDetails.getUsername()).getId(),
                    userService.getUserByEmail(userDetails.getUsername()).getRole()
            ));
        } else {
            // Return an unauthorized response
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
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


    @GetMapping("/get-user")
    public ResponseEntity<?> getUser(HttpSession session) throws Exception {

    	String username = (String) session.getAttribute("username");
    	User user = userService.getUserByEmail(username);
    	return ResponseEntity.ok(user);
    }


    @RequestMapping(value = "/currentuser", method = RequestMethod.GET)
    public ResponseEntity<?> getCurrentUser() {
        // Get the authentication object from the security context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Check if the user is authenticated
        if (authentication != null && authentication.isAuthenticated()) {
            // Extract the username from the authentication object
            String username = authentication.getName();

            // Retrieve the user details from the user service
            User user = userService.getUserByEmail(username);

            if (user != null) {
                // Return the user details as a response
                return ResponseEntity.ok(user);
            } else {
                // Return a not found response if the user details are not available
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
        } else {
            // Return an unauthorized response
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }
    }

}
