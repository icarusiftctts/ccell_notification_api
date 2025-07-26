package com.example.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class TopicSubscriptionId implements Serializable {
    private String token;  // FCM token
    private String topic;  // e.g., "all-users"

    // Constructors
    public TopicSubscriptionId() {}
    public TopicSubscriptionId(String token, String topic) {
        this.token = token;
        this.topic = topic;
    }

    // Getters
    public String getToken() { return token; }
    public String getTopic() { return topic; }

    // Setters
    public void setToken(String token) { this.token = token; }
    public void setTopic(String topic) { this.topic = topic; }

    // Required for composite keys
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TopicSubscriptionId that = (TopicSubscriptionId) o;
        return token.equals(that.token) && topic.equals(that.topic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token, topic);
    }
}