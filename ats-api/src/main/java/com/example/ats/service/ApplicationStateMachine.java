package com.example.ats.service;

import com.example.ats.domain.enums.ApplicationStage;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

@Component
public class ApplicationStateMachine {

    private final Map<ApplicationStage, Set<ApplicationStage>> transitions =
            new EnumMap<>(ApplicationStage.class);

    public ApplicationStateMachine() {

        transitions.put(
                ApplicationStage.APPLIED,
                EnumSet.of(
                        ApplicationStage.SCREENING,
                        ApplicationStage.REJECTED
                )
        );

        transitions.put(
                ApplicationStage.SCREENING,
                EnumSet.of(
                        ApplicationStage.INTERVIEW,
                        ApplicationStage.REJECTED
                )
        );

        transitions.put(
                ApplicationStage.INTERVIEW,
                EnumSet.of(
                        ApplicationStage.OFFER,
                        ApplicationStage.REJECTED
                )
        );

        transitions.put(
                ApplicationStage.OFFER,
                EnumSet.of(
                        ApplicationStage.HIRED,
                        ApplicationStage.REJECTED
                )
        );

        transitions.put(
                ApplicationStage.HIRED,
                EnumSet.noneOf(ApplicationStage.class)
        );

        transitions.put(
                ApplicationStage.REJECTED,
                EnumSet.noneOf(ApplicationStage.class)
        );
    }

    public boolean canTransition(
            ApplicationStage from,
            ApplicationStage to
    ) {
        return transitions
                .getOrDefault(from, EnumSet.noneOf(ApplicationStage.class))
                .contains(to);
    }

    public void validateTransition(
            ApplicationStage from,
            ApplicationStage to
    ) {
        if (!canTransition(from, to)) {
            throw new IllegalStateException(
                    "Invalid transition from " + from + " to " + to
            );
        }
    }
}
