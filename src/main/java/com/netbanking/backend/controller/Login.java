package com.netbanking.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netbanking.backend.service.SessionManager;

class LoginRequest {
    public String email;
    public String password;
}

class LoginResponse {
    public String token;
    public String message;
    public String status;
}

@RestController
// @RequestMapping("/login")
public class Login {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private SessionManager sessionManager;
        
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email, request.password));
            
            // if (!authentication.isAuthenticated()) {
            //     LoginResponse response = new LoginResponse();
            //     response.message = "Invalid login details";
            //     response.status = "error";
            //     return response;
            // }
        } catch (BadCredentialsException e) {
            LoginResponse response = new LoginResponse();
            response.message = "Invalid login details";
            response.status = "error";
            return response;
        }

        String token = sessionManager.createSession(request.email);

        LoginResponse response = new LoginResponse();
        response.token = token;
        response.message = "Login successful";
        response.status = "success";

        return response;
    }
}
