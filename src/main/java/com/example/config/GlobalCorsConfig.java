package com.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class GlobalCorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        System.out.println("CORS config loaded");
        registry.addMapping("/static/**")
                        .allowedOrigins("*")
                        .allowedMethods("GET")
                        .maxAge(3600);
        registry.addMapping("/**")
                .allowedOrigins("*") // Allow all origins for development
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(false);
    }


}
