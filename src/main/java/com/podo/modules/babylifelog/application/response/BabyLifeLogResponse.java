package com.podo.modules.babylifelog.application.response;

import java.time.LocalDateTime;
import java.util.UUID;

import com.podo.modules.babylifelog.domain.BabyLifeLogRecord;
import com.podo.modules.babylifelog.domain.BabyLifeLogType;

/**
 * Response DTO for a single baby life log entry.
 */
public record BabyLifeLogResponse(
        UUID id,
        BabyLifeLogType type,
        String content,
        LocalDateTime occurredAt
) {
    /**
     * Factory method to create a response from a domain entity.
     */
    public static BabyLifeLogResponse from(BabyLifeLogRecord record) {
        return new BabyLifeLogResponse(
                record.getId(),
                record.getType(),
                record.getContent(),
                record.getOccurredAt()
        );
    }
}

