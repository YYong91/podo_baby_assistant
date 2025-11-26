package com.podo.babylifelog.application.request;

import com.podo.babylifelog.application.response.BabyLifeLogListResponse;
import com.podo.shared.mediator.Request;

/**
 * Request to get all baby life logs.
 */
public record GetAllBabyLifeLogsRequest(
) implements Request<BabyLifeLogListResponse> {
}

