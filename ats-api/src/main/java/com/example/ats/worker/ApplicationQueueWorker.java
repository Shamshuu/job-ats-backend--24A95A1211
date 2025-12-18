package com.example.ats.worker;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ApplicationQueueWorker {

    @Scheduled(fixedDelay = 5000)
    public void run() {
        System.out.println("[RedisWorker] Worker is alive");
    }
}
