package com.podo.conversation.application.response;

import com.podo.conversation.domain.Conversation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Response DTO for conversation queries.
 */
public record ConversationResponse(
    UUID id,
    List<MessageResponse> messages,
    LocalDateTime createdAt,
    LocalDateTime lastUpdatedAt
) {
    public record MessageResponse(
        UUID id,
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

