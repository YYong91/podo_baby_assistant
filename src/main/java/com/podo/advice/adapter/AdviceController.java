package com.podo.advice.adapter;

import com.podo.advice.application.request.GenerateAdviceRequest;
import com.podo.advice.application.response.AdviceResponse;
import com.podo.shared.mediator.Mediator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * REST endpoints for LLM-based advice queries.
 * Uses Mediator pattern to dispatch requests to handlers.
 */
@RestController
@RequestMapping("/api/advice")
@RequiredArgsConstructor
public class AdviceController {

    private final Mediator mediator;

    @PostMapping
    public AdviceResponse getAdvice(@RequestBody GenerateAdviceRequest request) {
        return mediator.send(request);
    }
}
