package com.netbanking.backend.controller;

import java.util.List;
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

import com.netbanking.backend.dto.DTOConverter;
import com.netbanking.backend.dto.UserDTO;
import com.netbanking.backend.model.TransactionRecord;
import com.netbanking.backend.model.UserRecord;
import com.netbanking.backend.security.PasswordEncoder;
import com.netbanking.backend.security.UserDetailsImpl;
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
    public UserDTO getUserByToken() {
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        UserRecord user = userDetails.getUser();
        UserDTO userDTO = DTOConverter.from(user);
        return userDTO;
    }

    @GetMapping("/transactions")
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<TransactionRecord> getTransactions() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        UserRecord user = userDetails.getUser();

        return user.getTransactionRecords();
    }
}
