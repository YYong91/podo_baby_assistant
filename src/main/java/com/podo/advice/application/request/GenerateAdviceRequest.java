package com.podo.advice.application.request;

import com.podo.advice.application.response.AdviceResponse;
import com.podo.shared.mediator.Request;

/**
 * Request to generate advice based on a query.
 */
public record GenerateAdviceRequest(
    String query  // e.g., "아기 뒤집기 시기 언제야?"
) implements Request<AdviceResponse> {
}

