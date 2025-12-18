package com.example.ats.domain.entity;

import com.example.ats.domain.enums.ApplicationStage;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(
    name = "applications",
    uniqueConstraints = @UniqueConstraint(columnNames = {"job_id", "candidate_id"})
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "job_id")
    private Job job;

    @ManyToOne(optional = false)
    @JoinColumn(name = "candidate_id")
    private User candidate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationStage stage;

    @Column(nullable = false, updatable = false)
    private Instant appliedAt;

    @PrePersist
    void onCreate() {
        this.appliedAt = Instant.now();
        this.stage = ApplicationStage.APPLIED;
    }
}
