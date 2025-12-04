package com.podo.conversation.application.request;

import com.podo.conversation.application.response.ConversationResponse;
import com.podo.shared.mediator.Request;

/**
 * Request to get a conversation by ID.
 */
import java.util.UUID;

public record GetConversationRequest(
    UUID conversationId
) implements Request<ConversationResponse> {
}

