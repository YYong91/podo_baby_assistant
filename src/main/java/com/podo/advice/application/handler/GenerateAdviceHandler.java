package com.podo.advice.application.handler;

import com.podo.advice.application.request.GenerateAdviceRequest;
import com.podo.advice.application.response.AdviceResponse;
import com.podo.advice.domain.Advice;
import com.podo.advice.domain.AdviceGenerator;
import com.podo.shared.mediator.RequestHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Handler for GenerateAdviceRequest.
 */
@Component
@RequiredArgsConstructor
public class GenerateAdviceHandler 
    implements RequestHandler<GenerateAdviceRequest, AdviceResponse> {

    private final AdviceGenerator adviceGenerator;

    @Override
    public AdviceResponse handle(GenerateAdviceRequest request) {
        Advice advice = adviceGenerator.generate(request.query());
        return AdviceResponse.from(advice);
    }
}

