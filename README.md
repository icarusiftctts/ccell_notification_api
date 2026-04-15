
# C-Cell Notification API

A Spring Boot-based notification management system designed to deliver push notifications to mobile and web clients using Firebase Cloud Messaging (FCM). This API serves the C-Cell (Entrepreneurship Cell) application ecosystem at LNMIIT.

**Repository**: [`icarusiftctts/ccell_notification_api`](https://github.com/icarusiftctts/ccell_notification_api)  
**Language**: Java | **Framework**: Spring Boot 3.5.3 | **Last Updated**: April 2026

---

## 📋 Overview

The C-Cell Notification API is a robust backend service that:
- **Manages FCM tokens** from authenticated users and guest clients
- **Sends push notifications** to single users, topics, or broadcast to all users
- **Stores notification history** for audit and retrieval purposes
- **Authenticates senders** through email whitelisting
- **Supports topic-based subscriptions** for segmented messaging
- **Handles guest/anonymous users** with guest token tracking

This API enables real-time communication between the C-Cell organization and app users, facilitating announcements, event notifications, and time-sensitive updates.

---

## 🏗️ Architecture

### Technology Stack
- **Runtime**: Java 21
- **Framework**: Spring Boot 3.5.3 (LTS)
- **Database**: MySQL
- **Authentication**: Firebase Admin SDK (v9.5.0)
- **Messaging**: Firebase Cloud Messaging (FCM)
- **Build Tool**: Maven 3.9.6
- **Containerization**: Docker (Alpine + Maven multi-stage build)

### Core Components

#### **1. Models**
- **FCMToken**: Stores device tokens linked to users/guests with timestamps
  - Indexed on `userId` for optimized lookups
  - Tracks `isGuest` flag to distinguish authenticated vs. anonymous users
  - Automatic timestamp management via `createdAt` and `updatedAt`

- **Notification**: Represents posted notifications
  - Stores title, message, sender info, target topic
  - TEXT field for large message bodies
  - Audit timestamps: `datePosted`, `createdAt`

- **TopicSubscription**: Maps tokens to topics for segmented messaging
  - Enables users to opt-in/out of specific notification categories
  - Composite primary key: token + topic

#### **2. Services**
- **FCMService**: Core notification delivery logic
  - `sendPushAll()`: Broadcasts to all registered tokens
  - `sendToTopic()`: Topic-based delivery with hybrid support (native FCM + web token tracking)
  - `sendBatchedPushes()`: Handles batching for scalability (max 500 tokens per batch)

- **AuthService**: Access control
  - Manages whitelist of approved sender email addresses
  - Email-based authorization checks via `X-User-Email` header

#### **3. Controllers**
- **NotificationController** (`/api/notifications`)
  - POST: Create and broadcast notifications (requires `X-User-Email` header)
  - GET: Retrieve all notifications
  - POST `/register-token`: Register FCM tokens
  - GET `/is-authorized`: Check user authorization
  - GET `/approved-senders`: List authorized senders

- **TokenRequestController** (`/api/tokens`)
  - POST `/register`: Register device tokens

- **HealthController** (`/`, `/health`, `/ping`)
  - Liveness and readiness probes for orchestration

#### **4. DTOs**
- **TokenRegistrationRequest**: Payload for token registration (token, userId, isGuest flag)
- **TopicSubscriptionRequest**: Payload for topic subscription management

---

## 🚀 Quick Start

### Prerequisites
- Java 21 JDK
- Maven 3.9+
- MySQL 8.0+
- Firebase Admin SDK credentials (JSON key file)

### Local Development

1. **Clone the repository**
   ```bash
   git clone https://github.com/icarusiftctts/ccell_notification_api.git
   cd ccell_notification_api
