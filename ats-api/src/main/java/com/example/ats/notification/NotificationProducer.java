package com.example.ats.notification;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class NotificationProducer {

    private static final String QUEUE = "notification-queue";

    private final RedisTemplate<String, String> redisTemplate;

    public NotificationProducer(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void publish(String message) {
        redisTemplate.opsForList().leftPush(QUEUE, message);
    }
}
