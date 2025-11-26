package com.podo.conversation.application.response;

import com.podo.conversation.domain.Conversation;

import java.util.List;

/**
 * Response DTO for list of conversations.
 */
public record ConversationListResponse(
    List<ConversationResponse> conversations
) {
    /**
     * Factory method to create response from domain entities.
     */
    public static ConversationListResponse from(List<Conversation> domains) {
        return new ConversationListResponse(
            domains.stream().map(ConversationResponse::from).toList()
        );
    }
}

