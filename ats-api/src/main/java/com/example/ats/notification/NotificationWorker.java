package com.example.ats.notification;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class NotificationWorker {
    private static final String QUEUE = "notification-queue";
    private static final int MAX_RETRIES = 3;

    private final RedisTemplate<String, String> redisTemplate;
    private final EmailService emailService;

    @Autowired
    public NotificationWorker(RedisTemplate<String, String> redisTemplate, EmailService emailService) {
        this.redisTemplate = redisTemplate;
        this.emailService = emailService;
    }

    @Scheduled(fixedDelay = 2000)
    public void consume() {
        String message = redisTemplate.opsForList().rightPop(QUEUE);
        if (message != null) {
            boolean sent = false;
            int attempts = 0;
            Exception lastException = null;
            while (!sent && attempts < MAX_RETRIES) {
                try {
                    // Example: parse message as JSON or delimited string for real use
                    // For demo, send to a fixed address
                    emailService.sendEmail("recipient@example.com", "Notification", message);
                    sent = true;
                    System.out.println("[NOTIFICATION WORKER] Email sent: " + message);
                } catch (Exception e) {
                    attempts++;
                    lastException = e;
                    System.err.println("[NOTIFICATION WORKER] Email send failed (attempt " + attempts + "): " + e.getMessage());
                    try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
                }
            }
            if (!sent) {
                // Optionally, push to a dead-letter queue or log for manual review
                System.err.println("[NOTIFICATION WORKER] Failed to send notification after retries: " + message);
                if (lastException != null) lastException.printStackTrace();
            }
        }
    }
}
