package com.podo.conversation.application.request;

import com.podo.conversation.application.response.ConversationResponse;
import com.podo.shared.mediator.Request;

/**
 * Request to add a message to an existing conversation.
 */
import java.util.UUID;

public record AddMessageRequest(
    UUID conversationId,
    String role,      // e.g., "user", "assistant"
    String content
) implements Request<ConversationResponse> {
}

