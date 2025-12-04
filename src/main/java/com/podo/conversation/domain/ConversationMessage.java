package com.podo.conversation.domain;

import com.podo.shared.domain.EntityBase;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entity representing a single message within a conversation.
 */
public class ConversationMessage extends EntityBase {

    private final String role;      // e.g., "user", "assistant"
    private final String content;
    private final LocalDateTime createdAt;

    private ConversationMessage(UUID id, String role, String content, LocalDateTime createdAt) {
        super(id);
        this.role = role;
        this.content = content;
        this.createdAt = createdAt;
    }

    public static ConversationMessage create(String role, String content) {
        return new ConversationMessage(
            UUID.randomUUID(),
            role,
            content,
            LocalDateTime.now()
        );
    }

    public static ConversationMessage reconstitute(UUID id, String role, String content, LocalDateTime createdAt) {
        return new ConversationMessage(id, role, content, createdAt);
    }

    public String getRole() {
        return role;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}

