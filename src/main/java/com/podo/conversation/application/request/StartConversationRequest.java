package com.podo.conversation.application.request;

import com.podo.conversation.application.response.ConversationResponse;
import com.podo.shared.mediator.Request;

/**
 * Request to start a new conversation.
 */
public record StartConversationRequest(
) implements Request<ConversationResponse> {
}

