package com.example.ats.repository;

import com.example.ats.domain.entity.Job;
import com.example.ats.domain.enums.JobStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {

    List<Job> findByCompanyId(Long companyId);

    List<Job> findByCompanyIdAndStatus(Long companyId, JobStatus status);
}
