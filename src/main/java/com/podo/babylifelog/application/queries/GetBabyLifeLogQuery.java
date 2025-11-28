package com.podo.babylifelog.application.queries;

import com.podo.babylifelog.application.response.BabyLifeLogResponse;
import com.podo.shared.mediator.Request;

import java.util.UUID;

/**
 * Query to get a baby life log entry by id.
 */
public record GetBabyLifeLogQuery(UUID id) implements Request<BabyLifeLogResponse> {
}

