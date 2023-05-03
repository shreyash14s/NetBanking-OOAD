package com.netbanking.backend.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.netbanking.backend.model.TransactionRecord;
import com.netbanking.backend.model.UserRecord;
import com.netbanking.backend.repository.UserRecordRepository;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRecordRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    Logger logger = org.slf4j.LoggerFactory.getLogger(UserService.class);

    public List<UserRecord> getAllUsers() {
        return userRepository.findAll();
    }

    // public UserRecord getUserByToken(String token) {
    //     String email = SessionManager.getInstance().getSession(token);
    //     if (email == null) {
    //         logger.error("Invalid token");
    //         return null;
    //     }
    //     return userRepository.findUserByEmail(email);
    // }

    public Optional<UserRecord> getUserByAccountNumber(String accountNumber) {
        return userRepository.findUserByAccountNumber(accountNumber);
    }

    public Optional<UserRecord> getUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public UserRecord createUser(String email, String password, String name) {
        String accountNumber;
        do {
            accountNumber = String.format("1%010d", (int)(Math.random() * 1000000000));
        } while (getUserByAccountNumber(accountNumber) != null);

        UserRecord user = UserRecord.builder()
            .userEmail(email)
            .userPassword(passwordEncoder.encode(password))
            .userName(name)
            .build();
        
        return userRepository.save(user);
    }

    public void updateUser(UserRecord user) {
        userRepository.save(user);
    }

    public void deleteUser(UserRecord user) {
        userRepository.delete(user);
    }

    public void deleteAllUsers() {
        userRepository.deleteAll();
    }
}
