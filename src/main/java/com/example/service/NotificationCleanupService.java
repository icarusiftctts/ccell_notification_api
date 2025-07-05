package com.example.service;

import com.example.model.Notification;
import com.example.repository.NotificationRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationCleanupService {

    @Autowired
    private NotificationRepository notificationRepository;

    private final Logger logger = LoggerFactory.getLogger(NotificationCleanupService.class);

    @Scheduled(cron = "0 0 3 * * ?") // Runs daily at 3:00 AM
    public void deleteOldNotifications() {
        LocalDateTime threshold = LocalDateTime.now().minusWeeks(3);

        List<Notification> oldNotifications = notificationRepository.findByCreatedAtBefore(threshold);

        if (!oldNotifications.isEmpty()) {
            for (Notification notif : oldNotifications) {
                logger.info("Deleting Notification Backup: ID={}, From='{}', Message='{}', EventDate={}, CreatedAt={}",
                        notif.getId(), notif.getFrom(), notif.getMessage(), notif.getEventDate(), notif.getCreatedAt());
            }

            notificationRepository.deleteAll(oldNotifications);

            logger.info("✅ {} old notifications deleted", oldNotifications.size());
        } else {
            logger.info("🧼 No notifications to delete today");
        }
    }
}