package com.example.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import java.time.LocalDateTime;

@Entity
public class TopicSubscription {
    @EmbeddedId
    private TopicSubscriptionId id;
    private LocalDateTime subscribedAt;

    // Constructors
    public TopicSubscription() {
        this.subscribedAt = LocalDateTime.now();
    }
    public TopicSubscription(String token, String topic) {
        this.id = new TopicSubscriptionId(token, topic);
        this.subscribedAt = LocalDateTime.now();
    }

    // Getters
    public TopicSubscriptionId getId() { return id; }
    public LocalDateTime getSubscribedAt() { return subscribedAt; }

    // Setters
    public void setId(TopicSubscriptionId id) { this.id = id; }
    public void setSubscribedAt(LocalDateTime subscribedAt) {
        this.subscribedAt = subscribedAt;
    }
}