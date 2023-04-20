package com.netbanking.backend.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SessionManager {
    private static SessionManager instance = null;

    @Autowired
    private TokenGenerator tokenGenerator;

    private Map<String, String> sessionMap;

    private SessionManager() {
        sessionMap = new ConcurrentHashMap<>();
    }

    public static SessionManager getInstance() {
        if (instance == null) {
            synchronized (SessionManager.class) {
                if (instance == null) {
                    instance = new SessionManager();
                }
            }
        }
        return instance;
    }

    public String createSession(String email) {
        String token = tokenGenerator.generateToken();
        sessionMap.put(token, email);
        return token;
    }

    public void deleteSession(String token) {
        sessionMap.remove(token);
    }

    public String getSession(String token) {
        return sessionMap.getOrDefault(token, null);
    }
}
