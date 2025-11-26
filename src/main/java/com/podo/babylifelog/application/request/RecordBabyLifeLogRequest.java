package com.podo.babylifelog.application.request;

import com.podo.babylifelog.application.response.BabyLifeLogResponse;
import com.podo.shared.mediator.Request;

import java.time.LocalDateTime;

/**
 * Request to record a new baby life log entry.
 */
public record RecordBabyLifeLogRequest(
    String logType,           // e.g., "NAP", "MILESTONE"
    String description,       // e.g., "낮잠 두 번 잤어"
    LocalDateTime occurredAt  // When the event happened
) implements Request<BabyLifeLogResponse> {
}

