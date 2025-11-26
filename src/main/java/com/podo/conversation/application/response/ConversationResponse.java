package com.podo.conversation.application.response;

import com.podo.conversation.domain.Conversation;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Response DTO for conversation queries.
 */
public record ConversationResponse(
    String id,
    List<MessageResponse> messages,
    LocalDateTime createdAt,
    LocalDateTime lastUpdatedAt
) {
    public record MessageResponse(
        String id,
        String role,
        String content,
        LocalDateTime createdAt
    ) {}

    /**
     * Factory method to create response from domain entity.
     */
    public static ConversationResponse from(Conversation domain) {
        List<MessageResponse> messageResponses = domain.getMessages().stream()
            .map(msg -> new MessageResponse(
                msg.getId(),
                msg.getRole(),
                msg.getContent(),
                msg.getCreatedAt()
            ))
            .toList();

        return new ConversationResponse(
            domain.getId(),
            messageResponses,
            domain.getCreatedAt(),
            domain.getLastUpdatedAt()
        );
    }
}

