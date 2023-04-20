package com.netbanking.backend.service;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class TokenGenerator {
    private Random random = new SecureRandom();
    public String generateToken() {
        String hash = new BigInteger(130, random).toString(32).toLowerCase();
        // Limit hash to 8 characters
        if (hash.length() > 8) {
            hash = hash.substring(0, 8);
        } else if (hash.length() < 8) {
            // hash = hash; + "00000000".substring(0, 8 - hash.length());
        }
        return hash;
    }
}
