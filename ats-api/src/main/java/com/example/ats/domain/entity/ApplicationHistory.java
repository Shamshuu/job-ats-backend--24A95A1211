package com.example.ats.domain.entity;

import com.example.ats.domain.enums.ApplicationStage;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "application_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "application_id")
    private Application application;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationStage fromStage;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationStage toStage;

    @ManyToOne(optional = false)
    @JoinColumn(name = "changed_by")
    private User changedBy;

    @Column(nullable = false, updatable = false)
    private Instant changedAt;

    @PrePersist
    void onCreate() {
        this.changedAt = Instant.now();
    }
}
