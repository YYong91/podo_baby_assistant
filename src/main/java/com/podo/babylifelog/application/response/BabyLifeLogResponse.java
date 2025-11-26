package com.podo.babylifelog.application.response;

import com.podo.babylifelog.domain.BabyLifeLog;

import java.time.LocalDateTime;

/**
 * Response DTO for baby life log queries.
 */
public record BabyLifeLogResponse(
    Long id,
    String logType,
    String description,
    LocalDateTime occurredAt,
    LocalDateTime createdAt
) {
    /**
     * Factory method to create response from domain entity.
     */
    public static BabyLifeLogResponse from(BabyLifeLog domain) {
        return new BabyLifeLogResponse(
            domain.getId(),
            domain.getLogType().name(),
            domain.getDescription(),
            domain.getOccurredAt(),
            domain.getCreatedAt()
        );
    }
}

