package com.podo.babylifelog.application.queries;

import com.podo.babylifelog.application.response.BabyLifeLogResponse;
import com.podo.shared.mediator.Request;

import java.util.UUID;

import org.springframework.lang.NonNull;

/**
 * Query to get a baby life log entry by id.
 */
public record GetBabyLifeLogQuery(@NonNull UUID id) implements Request<BabyLifeLogResponse> {
}

