package com.podo.babylifelog.application.response;

import com.podo.babylifelog.domain.BabyLifeLogRecord;

import java.util.List;

/**
 * Response DTO for a list of baby life log entries.
 */
public record BabyLifeLogListResponse(List<BabyLifeLogResponse> logs) {

    /**
     * Factory method to create a response from a list of domain entities.
     */
    public static BabyLifeLogListResponse from(List<BabyLifeLogRecord> records) {
        return new BabyLifeLogListResponse(
                records.stream()
                        .map(BabyLifeLogResponse::from)
                        .toList()
        );
    }
}

