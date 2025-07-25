package com.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class GlobalCorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        System.out.println("CORS config loaded");
        registry.addMapping("/**")
                .allowedOrigins(
                        "http://localhost:5000",
                        "http://localhost:0000",
                        "https://ccell-b8c42.web.app",
                        "http://localhost:8000",
                        "https://www.ccellapp.in",
                        "https://ccellapp.in"
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(false);
    }


}
