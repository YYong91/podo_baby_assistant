package com.podo.babylifelog.application.command;

import com.podo.babylifelog.application.response.BabyLifeLogResponse;
import com.podo.babylifelog.domain.BabyLifeLogType;
import com.podo.shared.mediator.Request;

import java.time.LocalDateTime;

/**
 * Command to record a new baby life log entry.
 */
public record RecordBabyLifeLogCommand(
        BabyLifeLogType type,
        String content,
        LocalDateTime occurredAt
) implements Request<BabyLifeLogResponse> {
}

