package com.netbanking.backend.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.netbanking.backend.model.UserRecord;
import com.netbanking.backend.security.PasswordEncoder;
import com.netbanking.backend.service.UserService;

import ch.qos.logback.classic.Logger;

@RestController
@RequestMapping("/api/account")
public class Account {
    
    @Autowired
    private UserService userService;

    private Logger logger = (Logger) org.slf4j.LoggerFactory.getLogger(Account.class);

    @GetMapping("/get")
    @PreAuthorize("hasRole('ROLE_USER')")
    public UserRecord getUserByToken() {
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String username = userDetails.getUsername();

        Optional<UserRecord> user_opt = userService.getUserByEmail(username);
        if (user_opt.isEmpty()) {
            logger.warn("User not found");
            return null;
        }
        UserRecord user = user_opt.get();
        user.setUserPassword("");
        return user;
    }
}
