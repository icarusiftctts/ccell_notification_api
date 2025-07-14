package com.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class FCMToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String userId;
    private String fcmToken;
    private LocalDateTime updatedAt;

    public FCMToken(){}

    public FCMToken(String userId, String fcmToken, LocalDateTime updatedAt, long id){
        this.userId = userId;
        this.fcmToken = fcmToken;
        this.updatedAt = updatedAt;

        this.id = id;
    }

    public long getId(){
        return id;
    }

    public void setId(long id){
        this.id = id;
    }

    public void setUserId(String userId){
        this.userId = userId;
    }

    public String getUserId(){
        return userId;
    }

    public void setFcmToken(String fcmToken){
        this.fcmToken = fcmToken;
    }

    public String getFcmToken(){
        return fcmToken;
    }

    public void setUpdatedAt(LocalDateTime updatedAt){
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getUpdatedAt(){
        return updatedAt;
    }


}
