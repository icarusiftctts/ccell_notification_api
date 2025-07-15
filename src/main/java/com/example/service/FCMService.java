    package com.example.service;

    import com.google.firebase.messaging.Message;
    import com.google.firebase.messaging.Notification;
    import com.google.firebase.messaging.FirebaseMessaging;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;

    @Service
    public class FCMService {

        @Autowired
        private FirebaseMessaging firebaseMessaging;

        public void sendPush (String fcmToken, String title, String messageBody){
            Message message = Message.builder()
                    .setToken(fcmToken)
                    .setNotification(Notification.builder()
                            .setTitle(title)
                            .setBody(messageBody)
                            .build())
                    .build();
            try {
                String response = firebaseMessaging.send(message);
                System.out.println("Notification sent: " + response);
            } catch (Exception e){
                System.out.println("Notification error: Not Sent, " + e.getMessage());
            }
        }

        public void sendPushAll (String title, String messageBody){
            Message message = Message.builder()
                    .setTopic("all-users")
                    .setNotification(Notification.builder()
                            .setTitle(title)
                            .setBody(messageBody)
                            .build())
                    .build();
            try {
                String response = firebaseMessaging.send(message);
                System.out.println("Notification sent: " + response);
            } catch (Exception e){
                System.out.println("Notification error: Not Sent, " + e.getMessage());
            }
        }
    }
