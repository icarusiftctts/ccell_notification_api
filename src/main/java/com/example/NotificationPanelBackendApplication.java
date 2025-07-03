package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;
import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class NotificationPanelBackendApplication {
    public static void main(String[] args) {
        System.out.println("Starting Spring Boot...");
        try{
            SpringApplication.run(NotificationPanelBackendApplication.class, args);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @PostConstruct
    public void init(){
        // Setting Spring Boot default timezone to IST
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Kolkata"));
    }
}
