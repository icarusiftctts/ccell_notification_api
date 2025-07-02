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
                        //"https://your-flutter-web-app.web.app",   // Firebase Hosting or Vercel URL
                        "http://localhost:5000",
                        "http://localhost:0000",
                        "http://localhost:8000" // Local Flutter Web Dev Server
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(false); // Set true only if using cookies
    }


}
