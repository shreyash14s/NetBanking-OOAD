package com.netbanking.backend.security;

import java.security.SecureRandom;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoder {
    private int strength = 10;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public PasswordEncoder() {
        bCryptPasswordEncoder = new BCryptPasswordEncoder(strength, new SecureRandom());
    }

    public String hashPassword(String password) {
        return bCryptPasswordEncoder.encode(password);
    }
}
