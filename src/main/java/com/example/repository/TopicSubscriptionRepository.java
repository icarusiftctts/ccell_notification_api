package com.example.repository;

import com.example.model.TopicSubscription;
import com.example.model.TopicSubscriptionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicSubscriptionRepository
        extends JpaRepository<TopicSubscription, TopicSubscriptionId> {

    List<TopicSubscription> findByIdTopic(String topic);
    boolean existsByIdTokenAndIdTopic(String token, String topic);
    void deleteByIdToken(String token);
    void deleteByIdTokenAndIdTopic(String token, String topic);
}