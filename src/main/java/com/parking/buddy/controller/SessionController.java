package com.parking.buddy.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class SessionController {

    @PostMapping("/store")
    public String storeDataInSession(@RequestParam("key") String key, @RequestParam("value") String value, HttpSession session) {
        session.setAttribute(key, value);
        return "Data stored in session successfully";
    }

    @GetMapping("/retrieve")
    public String retrieveDataFromSession(@RequestParam("key") String key, HttpSession session) {
        String value = (String) session.getAttribute(key);
        return "Value for key '" + key + "': " + value;
    }

}
