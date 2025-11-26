package com.podo.conversation.application.request;

import com.podo.conversation.application.response.ConversationListResponse;
import com.podo.shared.mediator.Request;

/**
 * Request to get recent conversations.
 */
public record GetRecentConversationsRequest(
    int limit
) implements Request<ConversationListResponse> {
}

