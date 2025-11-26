package com.podo.babylifelog.domain;

import java.time.LocalDateTime;

/**
 * Aggregate root for baby life log events.
 * Pure domain entity with no framework dependencies.
 */
public class BabyLifeLog {

    private Long id;
    private final LogType logType;
    private final String description;
    private final LocalDateTime occurredAt;
    private final LocalDateTime createdAt;

    private BabyLifeLog(Long id, LogType logType, String description, LocalDateTime occurredAt, LocalDateTime createdAt) {
        this.id = id;
        this.logType = logType;
        this.description = description;
        this.occurredAt = occurredAt;
        this.createdAt = createdAt;
    }

    /**
     * Factory method to create a new BabyLifeLog.
     */
    public static BabyLifeLog create(LogType logType, String description, LocalDateTime occurredAt) {
        return new BabyLifeLog(null, logType, description, occurredAt, LocalDateTime.now());
    }

    /**
     * Reconstitute from persistence.
     */
    public static BabyLifeLog reconstitute(Long id, LogType logType, String description,
                                            LocalDateTime occurredAt, LocalDateTime createdAt) {
        return new BabyLifeLog(id, logType, description, occurredAt, createdAt);
    }

    public Long getId() {
        return id;
    }

    public LogType getLogType() {
        return logType;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getOccurredAt() {
        return occurredAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}

