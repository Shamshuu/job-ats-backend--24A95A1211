package com.example.ats.service;

import com.example.ats.domain.entity.Job;
import com.example.ats.domain.entity.User;
import com.example.ats.domain.enums.JobStatus;
import com.example.ats.repository.JobRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService {

    private final JobRepository jobRepository;

    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public Job createJob(Job job, User recruiter) {
        job.setCompany(recruiter.getCompany());
        job.setStatus(JobStatus.OPEN);
        return jobRepository.save(job);
    }

    public List<Job> getJobsByCompany(Long companyId) {
        return jobRepository.findByCompanyId(companyId);
    }

    public List<Job> getJobsByCompanyAndStatus(Long companyId, JobStatus status) {
        return jobRepository.findByCompanyIdAndStatus(companyId, status);
    }
    
    public Job updateJob(Job job) {
        return jobRepository.save(job);
    }

    public void deleteJob(Long jobId) {
        jobRepository.deleteById(jobId);
    }
}
