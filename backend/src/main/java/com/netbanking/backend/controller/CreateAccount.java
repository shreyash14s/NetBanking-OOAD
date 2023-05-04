package com.netbanking.backend.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netbanking.backend.model.UserRecord;
import com.netbanking.backend.service.UserService;

class CreateAccountRequest {
    public String email;
    public String password;
    public String name;
}

class CreateAccountResponse {
    public String message;
    public String status;
}

@RestController
public class CreateAccount {
    
    @Autowired
    private UserService userService;

    private Logger logger = (Logger) org.slf4j.LoggerFactory.getLogger(Account.class);

    @PostMapping("/api/create-account")
    public CreateAccountResponse createAccount(@RequestBody CreateAccountRequest request) {
        logger.error("Creating account for " + request.email);
        CreateAccountResponse response = new CreateAccountResponse();
        Optional<UserRecord> user = userService.getUserByEmail(request.email);
        if (user.isPresent()) {
            response.message = "User already exists";
            response.status = "error";
            return response;
        }

        userService.createUser(request.email, request.password, request.name);

        response.message = "Account created successfully";
        response.status = "success";
        return response;
    }
}
