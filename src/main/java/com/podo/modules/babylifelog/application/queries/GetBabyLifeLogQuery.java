package com.podo.modules.babylifelog.application.queries;

import com.podo.modules.babylifelog.application.response.BabyLifeLogResponse;
import com.podo.shared.mediator.Request;

import java.util.UUID;

import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Query to get a baby life log entry by id.
 */
public record GetBabyLifeLogQuery(@PathVariable @NonNull UUID id) implements Request<BabyLifeLogResponse> {
}

