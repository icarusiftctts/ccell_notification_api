package com.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "file:secrets.properties", ignoreResourceNotFound = true)
public class LocalSecretsConfig {

}

