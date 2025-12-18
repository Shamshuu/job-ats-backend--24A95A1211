package com.example.ats.notification;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class NotificationWorker {

    private static final String QUEUE = "notification-queue";

    private final RedisTemplate<String, String> redisTemplate;

    public NotificationWorker(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Scheduled(fixedDelay = 2000)
    public void consume() {
        String message = redisTemplate.opsForList().rightPop(QUEUE);

        if (message != null) {
            System.out.println(
                "[NOTIFICATION WORKER] Consumed event: " + message
            );
        }
    }
}
