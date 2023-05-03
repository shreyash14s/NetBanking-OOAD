package com.netbanking.backend.controller;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netbanking.backend.model.UserRecord;
import com.netbanking.backend.service.UserService;

@RestController
public class ListAccount {
    @Autowired
    private UserService userService;

    private Logger logger = (Logger) org.slf4j.LoggerFactory.getLogger(Account.class);

    @GetMapping("/api/list-account")
    public Iterable<UserRecord> getAllUsers() {
        return userService.getAllUsers();
    }
}
