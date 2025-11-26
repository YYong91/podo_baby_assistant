package com.podo.babylifelog.domain;

import java.time.LocalDate;
import java.util.List;

/**
 * Domain service for BabyLifeLog business logic.
 * Contains domain logic that doesn't naturally belong to a single entity.
 */
public class BabyLifeLogDomainService {

    /**
     * Calculates the total nap count for a given day.
     */
    public int countNapsForDay(List<BabyLifeLog> logs, LocalDate date) {
        return (int) logs.stream()
            .filter(log -> log.getLogType() == LogType.NAP)
            .filter(log -> log.getOccurredAt().toLocalDate().equals(date))
            .count();
    }

    /**
     * Finds the most recent milestone.
     */
    public BabyLifeLog findLatestMilestone(List<BabyLifeLog> logs) {
        return logs.stream()
            .filter(log -> log.getLogType() == LogType.MILESTONE)
            .max((a, b) -> a.getOccurredAt().compareTo(b.getOccurredAt()))
            .orElse(null);
    }
}

