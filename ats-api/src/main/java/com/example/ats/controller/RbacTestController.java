package com.example.ats.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class RbacTestController {

    @GetMapping("/candidate")
    @PreAuthorize("hasRole('CANDIDATE')")
    public String candidateOnly() {
        return "CANDIDATE_OK";
    }

    @GetMapping("/recruiter")
    @PreAuthorize("hasRole('RECRUITER')")
    public String recruiterOnly() {
        return "RECRUITER_OK";
    }

    @GetMapping("/manager")
    @PreAuthorize("hasRole('HIRING_MANAGER')")
    public String managerOnly() {
        return "MANAGER_OK";
    }
}
