package com.podo.babylifelog.application.query;

import com.podo.babylifelog.application.response.BabyLifeLogListResponse;
import com.podo.shared.mediator.Request;

/**
 * Query to get all baby life log entries.
 */
public record GetAllBabyLifeLogsQuery() implements Request<BabyLifeLogListResponse> {
}

