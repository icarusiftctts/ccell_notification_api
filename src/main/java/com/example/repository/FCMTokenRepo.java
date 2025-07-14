package com.example.repository;

import com.example.model.FCMToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FCMTokenRepo extends JpaRepository<FCMToken, Long> {
    Optional<FCMToken> findByUserId(String userId);
    Optional<FCMToken> findByFcmToken(String fcmToken);


}
