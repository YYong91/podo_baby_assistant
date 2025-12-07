package com.podo.modules.babylifelog.application.response;

import java.util.List;

import com.podo.modules.babylifelog.domain.BabyLifeLogRecord;

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

