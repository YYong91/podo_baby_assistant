package com.podo.conversation.adapter;

import com.podo.conversation.application.request.AddMessageRequest;
import com.podo.conversation.application.request.GetConversationRequest;
import com.podo.conversation.application.request.GetRecentConversationsRequest;
import com.podo.conversation.application.request.StartConversationRequest;
import com.podo.conversation.application.response.ConversationListResponse;
import com.podo.conversation.application.response.ConversationResponse;
import com.podo.shared.mediator.Mediator;
import lombok.RequiredArgsConstructor;
import java.util.UUID;
import org.springframework.web.bind.annotation.*;

/**
 * REST endpoints for conversation history.
 * Uses Mediator pattern to dispatch requests to handlers.
 */
@RestController
@RequestMapping("/api/conversations")
@RequiredArgsConstructor
public class ConversationController {

    private final Mediator mediator;

    @PostMapping
    public ConversationResponse startConversation() {
        return mediator.send(new StartConversationRequest());
    }

    @PostMapping("/{conversationId}/messages")
    public ConversationResponse addMessage(
        @PathVariable UUID conversationId,
        @RequestBody AddMessageRequest request
    ) {
        // Create new request with path variable
        AddMessageRequest fullRequest = new AddMessageRequest(
            conversationId,
            request.role(),
            request.content()
        );
        return mediator.send(fullRequest);
    }

    @GetMapping("/{conversationId}")
    public ConversationResponse getConversation(@PathVariable UUID conversationId) {
        return mediator.send(new GetConversationRequest(conversationId));
    }

    @GetMapping
    public ConversationListResponse getRecentConversations(
        @RequestParam(defaultValue = "10") int limit
    ) {
        return mediator.send(new GetRecentConversationsRequest(limit));
    }
}
