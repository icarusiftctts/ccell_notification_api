package com.example.service;

import org.springframework.stereotype.Service;

import java.util.Set;


@Service
public class AuthService {

    private static final Set<String> authorizedEmails = Set.of(
            "c-cell@lnmiit.ac.in",
            "24ucs202@lnmiit.ac.in"
    );

    public boolean isAuthorized(String email) {
        return authorizedEmails.contains(email.toLowerCase());
    }
}