package com.example.ocr.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import model.UserDetails;

/**
 * used for authentication.
 */
@RestController
public class Authenticator {
    
    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(@RequestBody UserDetails userDetails) {
        // use user Details and do ldap authentication and authorization.
        // generate JWT token.

        return new ResponseEntity<String>("token", HttpStatus.OK);

    }
}
