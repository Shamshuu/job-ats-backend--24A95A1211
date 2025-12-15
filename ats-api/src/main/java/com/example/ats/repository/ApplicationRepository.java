package com.example.ats.repository;

import com.example.ats.domain.entity.Application;
import com.example.ats.domain.enums.ApplicationStage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    Optional<Application> findByJobIdAndCandidateId(Long jobId, Long candidateId);

    List<Application> findByJobId(Long jobId);

    List<Application> findByJobIdAndStage(Long jobId, ApplicationStage stage);

    List<Application> findByCandidateId(Long candidateId);
}
