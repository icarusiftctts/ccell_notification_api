package com.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("local") // modified by cursor - only load local secrets in local profile
public class LocalSecretsConfig {
    // modified by cursor - This will only be active in local development
    // On Render, this configuration will be ignored due to production profile
}

