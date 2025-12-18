package com.example.ats.notification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationEvent {

    private String type;      // APPLICATION_SUBMITTED, STAGE_CHANGED
    private Long applicationId;
    private String recipientEmail;
}
