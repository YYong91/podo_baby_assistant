package com.podo.messageorchestrator.application.request;

import com.podo.messageorchestrator.application.response.MessageResponse;
import com.podo.shared.mediator.Request;

/**
 * Request to process an incoming natural language message.
 */
public record ProcessMessageRequest(
    String content  // e.g., "포도야, 오늘 낮잠 두 번 잤어"
) implements Request<MessageResponse> {
}

