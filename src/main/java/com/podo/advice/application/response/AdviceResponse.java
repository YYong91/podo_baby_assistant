package com.podo.advice.application.response;

import com.podo.advice.domain.Advice;

import java.time.LocalDateTime;

/**
 * Response DTO for advice queries.
 */
import java.util.UUID;

public record AdviceResponse(
    UUID id,
    String query,
    String content,
    LocalDateTime generatedAt
) {
    /**
     * Factory method to create response from domain entity.
     */
    public static AdviceResponse from(Advice domain) {
        return new AdviceResponse(
            domain.getId(),
            domain.getQuery(),
            domain.getContent(),
            domain.getGeneratedAt()
        );
    }
}

