package com.podo.messageorchestrator.application.handler;

import com.podo.messageorchestrator.application.request.ProcessMessageRequest;
import com.podo.messageorchestrator.application.response.MessageResponse;
import com.podo.messageorchestrator.domain.Message;
import com.podo.messageorchestrator.domain.MessageRouter;
import com.podo.messageorchestrator.domain.MessageType;
import com.podo.shared.mediator.RequestHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Handler for ProcessMessageRequest.
 * Orchestrates message processing by routing to appropriate domains.
 */
@Component
@RequiredArgsConstructor
public class ProcessMessageHandler 
    implements RequestHandler<ProcessMessageRequest, MessageResponse> {

    private final MessageRouter messageRouter;

    @Override
    public MessageResponse handle(ProcessMessageRequest request) {
        // Create domain message
        Message message = Message.create(request.content(), MessageType.UNKNOWN);
        
        // Route to appropriate domain
        MessageRouter.RoutingResult result = messageRouter.route(message);
        
        // TODO: Based on routing result, dispatch to appropriate domain handler
        // For now, return a placeholder response
        return MessageResponse.success(
            message.getId(),
            "Processed: " + request.content(),
            result.targetDomain()
        );
    }
}

