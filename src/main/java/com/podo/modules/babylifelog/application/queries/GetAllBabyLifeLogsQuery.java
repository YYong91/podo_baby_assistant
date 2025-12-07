package com.podo.modules.babylifelog.application.queries;

import com.podo.modules.babylifelog.application.response.BabyLifeLogListResponse;
import com.podo.shared.mediator.Request;

/**
 * Query to get all baby life log entries.
 */
public record GetAllBabyLifeLogsQuery() implements Request<BabyLifeLogListResponse> {
}

