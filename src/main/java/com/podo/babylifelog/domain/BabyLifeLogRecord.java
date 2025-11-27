package com.podo.babylifelog.domain;

import com.podo.shared.domain.EntityBase;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Aggregate root for baby life log events.
 * Pure domain entity with no framework dependencies.
 */
public class BabyLifeLogRecord extends EntityBase {

    private final BabyLifeLogType type;
    private final String content;
    private final LocalDateTime occurredAt;

    private BabyLifeLogRecord(BabyLifeLogType type, String content, LocalDateTime occurredAt) {
        super(UUID.randomUUID());
        this.type = type;
        this.content = content;
        this.occurredAt = occurredAt;
    }

    private BabyLifeLogRecord(UUID id, BabyLifeLogType type, String content, LocalDateTime occurredAt) {
        super(id);
        this.type = type;
        this.content = content;
        this.occurredAt = occurredAt;
    }

    /**
     * Factory method to create a new BabyLifeLogRecord.
     * Sets createdAt to the current time.
     */
    public static BabyLifeLogRecord create(BabyLifeLogType type, String content, LocalDateTime occurredAt) {
        return new BabyLifeLogRecord(type, content, occurredAt);
    }

    public static BabyLifeLogRecord reconstitute(UUID id, BabyLifeLogType type, String content,
            LocalDateTime occurredAt) {
        return new BabyLifeLogRecord(id, type, content, occurredAt);
    }

    public BabyLifeLogType getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getOccurredAt() {
        return occurredAt;
    }
}

