package com.podo.babylifelog.application.response;

import com.podo.babylifelog.domain.BabyLifeLog;

import java.util.List;

/**
 * Response DTO for list of baby life logs.
 */
public record BabyLifeLogListResponse(
    List<BabyLifeLogResponse> logs
) {
    /**
     * Factory method to create response from domain entities.
     */
    public static BabyLifeLogListResponse from(List<BabyLifeLog> domains) {
        return new BabyLifeLogListResponse(
            domains.stream().map(BabyLifeLogResponse::from).toList()
        );
    }
}

