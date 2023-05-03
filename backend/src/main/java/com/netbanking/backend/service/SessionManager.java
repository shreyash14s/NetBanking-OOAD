package com.netbanking.backend.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SessionManager {
    private static SessionManager instance = null;

    @Autowired
    private TokenGenerator tokenGenerator;

    private Map<String, String> sessionMap;

    Logger logger = org.slf4j.LoggerFactory.getLogger(SessionManager.class);

    private SessionManager() {
        // locked hashmap
        sessionMap = new HashMap<>();
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
        logger.error("Session created for " + sessionMap.get(token));
        logger.error("Token created: " + token);
        logger.error("Session map: " + this);
        return token;
    }

    public void deleteSession(String token) {
        sessionMap.remove(token);
    }

    public String getSession(String token) {
        logger.error("Session map get: " + this);
        return sessionMap.getOrDefault(token, null);
    }
}
