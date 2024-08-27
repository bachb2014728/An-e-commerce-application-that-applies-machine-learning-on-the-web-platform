package com.example.backend.jwt;

import org.springframework.security.core.userdetails.UserDetails;

public class UserContext {
    private static final ThreadLocal<UserDetails> userContext = new ThreadLocal<>();

    public static void setUser(UserDetails user) {
        userContext.set(user);
    }

    public static UserDetails getUser() {
        return userContext.get();
    }

    public static void clear() {
        userContext.remove();
    }
}

