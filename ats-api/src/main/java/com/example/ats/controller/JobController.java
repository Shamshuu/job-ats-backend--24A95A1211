package com.example.ats.controller;

import com.example.ats.domain.entity.Job;
import com.example.ats.domain.entity.User;
import com.example.ats.domain.enums.JobStatus;
import com.example.ats.service.JobService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    // Recruiter creates job
    @PreAuthorize("hasRole('RECRUITER')")
    @PostMapping
    public Job createJob(@RequestBody Job job,
                          @AuthenticationPrincipal User recruiter) {
        return jobService.createJob(job, recruiter);
    }

    // Recruiter gets own company jobs
    @PreAuthorize("hasRole('RECRUITER')")
    @GetMapping
    public List<Job> getCompanyJobs(@AuthenticationPrincipal User recruiter) {
        return jobService.getJobsByCompany(
                recruiter.getCompany().getId()
        );
    }

    // Recruiter updates job
    @PreAuthorize("hasRole('RECRUITER')")
    @PutMapping("/{id}")
    public Job updateJob(@PathVariable Long id,
                         @RequestBody Job job) {
        job.setId(id);
        return jobService.updateJob(job);
    }

    // Recruiter deletes job
    @PreAuthorize("hasRole('RECRUITER')")
    @DeleteMapping("/{id}")
    public void deleteJob(@PathVariable Long id) {
        jobService.deleteJob(id);
    }

    // PUBLIC: jobs by company
    @GetMapping("/company/{companyId}")
    public List<Job> getJobsByCompany(@PathVariable Long companyId) {
        return jobService.getJobsByCompany(companyId);
    }

    // PUBLIC: jobs by company + status
    @GetMapping("/company/{companyId}/status/{status}")
    public List<Job> getJobsByCompanyAndStatus(
            @PathVariable Long companyId,
            @PathVariable String status
    ) {
        return jobService.getJobsByCompanyAndStatus(
                companyId,
                JobStatus.valueOf(status.toUpperCase())
        );
    }
}
