package com.netbanking.backend.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.netbanking.backend.security.JwtUtils;
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
public class Login {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    Logger logger = (Logger) org.slf4j.LoggerFactory.getLogger(Login.class);

    @Autowired
    private SessionManager sessionManager;

    @PostMapping("/api/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        // try {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email, request.password));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtils.generateJwtToken(authentication);

        // UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        // List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
        //         .collect(Collectors.toList());

        //     // if (!authentication.isAuthenticated()) {
        //     //     LoginResponse response = new LoginResponse();
        //     //     response.message = "Invalid login details";
        //     //     response.status = "error";
        //     //     return response;
        //     // }
        // } catch (BadCredentialsException e) {
        //     LoginResponse response = new LoginResponse();
        //     response.message = "Invalid login details";
        //     response.status = "error";
        //     return response;
        // }

        // String token = sessionManager.createSession(request.email);

        // logger.error("Login successful for " + request.email);
        // logger.error("Token: " + token);

        LoginResponse response = new LoginResponse();
        response.token = jwt;
        response.message = "Login successful";
        response.status = "success";

        return response;
    }
}
