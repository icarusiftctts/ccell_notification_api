package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
}
