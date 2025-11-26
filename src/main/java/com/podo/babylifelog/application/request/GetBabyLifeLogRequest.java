package com.podo.babylifelog.application.request;

import com.podo.babylifelog.application.response.BabyLifeLogResponse;
import com.podo.shared.mediator.Request;

/**
 * Request to get a baby life log by ID.
 */
public record GetBabyLifeLogRequest(
    Long id
) implements Request<BabyLifeLogResponse> {
}

