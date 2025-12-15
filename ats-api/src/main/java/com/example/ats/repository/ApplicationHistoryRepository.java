package com.example.ats.repository;

import com.example.ats.domain.entity.ApplicationHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationHistoryRepository extends JpaRepository<ApplicationHistory, Long> {

    List<ApplicationHistory> findByApplicationIdOrderByChangedAtAsc(Long applicationId);
}
