package com.example.ats.service;

import com.example.ats.domain.enums.ApplicationStage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationStateMachineTest {
    @Test
    void testValidTransition() {
        ApplicationStateMachine sm = new ApplicationStateMachine();
        assertTrue(sm.isValidTransition(ApplicationStage.APPLIED, ApplicationStage.SCREENING));
        assertTrue(sm.isValidTransition(ApplicationStage.SCREENING, ApplicationStage.INTERVIEW));
        assertTrue(sm.isValidTransition(ApplicationStage.INTERVIEW, ApplicationStage.OFFER));
        assertTrue(sm.isValidTransition(ApplicationStage.OFFER, ApplicationStage.HIRED));
    }

    @Test
    void testInvalidTransition() {
        ApplicationStateMachine sm = new ApplicationStateMachine();
        assertFalse(sm.isValidTransition(ApplicationStage.APPLIED, ApplicationStage.HIRED));
        assertFalse(sm.isValidTransition(ApplicationStage.SCREENING, ApplicationStage.OFFER));
        assertFalse(sm.isValidTransition(ApplicationStage.INTERVIEW, ApplicationStage.HIRED));
    }
}
