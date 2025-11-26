package com.podo.conversation.application.request;

import com.podo.conversation.application.response.ConversationResponse;
import com.podo.shared.mediator.Request;

/**
 * Request to get a conversation by ID.
 */
public record GetConversationRequest(
    String conversationId
) implements Request<ConversationResponse> {
}

