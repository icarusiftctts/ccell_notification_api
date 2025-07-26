package com.example.repository;

import com.example.model.FCMToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FCMTokenRepo extends JpaRepository<FCMToken, Long> {
    // Existing queries
    Optional<FCMToken> findByUserId(String userId);
    Optional<FCMToken> findByFcmToken(String fcmToken);
    boolean existsByFcmToken(String fcmToken);

    // NEW: Find all tokens for a user (guest or authenticated)
    List<FCMToken> findAllByUserId(String userId);

    // NEW: Find tokens by guest status
    List<FCMToken> findByIsGuest(boolean isGuest);

    // NEW: Bulk update user ID (for guest-to-user conversion)
    @Modifying
    @Transactional
    @Query("UPDATE FCMToken t SET t.userId = :newUserId, t.isGuest = false WHERE t.userId = :oldUserId")
    int updateUserId(String oldUserId, String newUserId);

    // NEW: Cleanup expired tokens (called by scheduled job)
    @Modifying
    @Transactional
    @Query("DELETE FROM FCMToken t WHERE t.createdAt < :cutoff")
    int deleteExpiredTokens(LocalDateTime cutoff);


}