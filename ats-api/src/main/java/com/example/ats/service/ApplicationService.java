package com.example.ats.service;

import com.example.ats.domain.entity.*;
import com.example.ats.domain.enums.ApplicationStage;
import com.example.ats.notification.NotificationProducer;
import com.example.ats.repository.ApplicationHistoryRepository;
import com.example.ats.repository.ApplicationRepository;
import com.example.ats.repository.JobRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final ApplicationHistoryRepository historyRepository;
    private final JobRepository jobRepository;
    private final ApplicationStateMachine stateMachine;
    private final NotificationProducer notificationProducer;


    public ApplicationService(
            ApplicationRepository applicationRepository,
            ApplicationHistoryRepository historyRepository,
            JobRepository jobRepository,
            ApplicationStateMachine stateMachine,
            NotificationProducer notificationProducer
    ) {
        this.applicationRepository = applicationRepository;
        this.historyRepository = historyRepository;
        this.jobRepository = jobRepository;
        this.stateMachine = stateMachine;
        this.notificationProducer = notificationProducer;

    }

    @Transactional
    public Application applyToJob(Long jobId, User candidate) {

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        applicationRepository
                .findByJobIdAndCandidateId(jobId, candidate.getId())
                .ifPresent(a -> {
                    throw new IllegalStateException("Already applied to this job");
                });

        Application application = Application.builder()
                .job(job)
                .candidate(candidate)
                .stage(ApplicationStage.APPLIED)
                .build();

        notificationProducer.publish(
            "APPLICATION_SUBMITTED:" + application.getId()
        );


                

        return applicationRepository.save(application);
    }

    @Transactional
    public Application changeStage(
            Long applicationId,
            ApplicationStage newStage,
            User changedBy
    ) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        ApplicationStage currentStage = application.getStage();

        stateMachine.validateTransition(currentStage, newStage);

        application.setStage(newStage);
        Application saved = applicationRepository.save(application);

        ApplicationHistory history = ApplicationHistory.builder()
                .application(saved)
                .fromStage(currentStage)
                .toStage(newStage)
                .changedBy(changedBy)
                .build();

        historyRepository.save(history);

        notificationProducer.publish(
            "STAGE_CHANGED:" + saved.getId()
        );


        return saved;
    }
}
