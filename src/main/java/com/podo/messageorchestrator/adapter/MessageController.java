package com.podo.messageorchestrator.adapter;

import com.podo.messageorchestrator.application.request.ProcessMessageRequest;
import com.podo.messageorchestrator.application.response.MessageResponse;
import com.podo.shared.mediator.Mediator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST endpoint to receive natural language input (voice/text).
 * Uses Mediator pattern to dispatch requests to handlers.
 */
@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    private final Mediator mediator;

    @PostMapping
    public MessageResponse handleMessage(@RequestBody ProcessMessageRequest request) {
        return mediator.send(request);
    }
}
