package com.example.ats.controller;

import com.example.ats.domain.entity.Application;
import com.example.ats.domain.entity.User;
import com.example.ats.domain.enums.ApplicationStage;
import com.example.ats.repository.ApplicationRepository;
import com.example.ats.service.ApplicationService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    private final ApplicationService applicationService;
    private final ApplicationRepository applicationRepository;

    public ApplicationController(
            ApplicationService applicationService,
            ApplicationRepository applicationRepository
    ) {
        this.applicationService = applicationService;
        this.applicationRepository = applicationRepository;
    }

    // 1️⃣ Candidate applies to a job
    @PreAuthorize("hasRole('CANDIDATE')")
    @PostMapping("/job/{jobId}")
    public Application apply(
            @PathVariable Long jobId,
            @AuthenticationPrincipal User candidate
    ) {
        return applicationService.applyToJob(jobId, candidate);
    }

    // 2️⃣ Candidate views own applications
    @PreAuthorize("hasRole('CANDIDATE')")
    @GetMapping("/me")
    public List<Application> myApplications(
            @AuthenticationPrincipal User candidate
    ) {
        return applicationRepository.findByCandidateId(candidate.getId());
    }

    // 3️⃣ Recruiter views applications for a job
    @PreAuthorize("hasRole('RECRUITER')")
    @GetMapping("/job/{jobId}")
    public List<Application> applicationsForJob(
            @PathVariable Long jobId
    ) {
        return applicationRepository.findByJobId(jobId);
    }

    // 4️⃣ Recruiter changes application stage
    @PreAuthorize("hasRole('RECRUITER')")
    @PutMapping("/{applicationId}/stage/{stage}")
    public Application changeStage(
            @PathVariable Long applicationId,
            @PathVariable String stage,
            @AuthenticationPrincipal User recruiter
    ) {
        return applicationService.changeStage(
                applicationId,
                ApplicationStage.valueOf(stage.toUpperCase()),
                recruiter
        );
    }
}
