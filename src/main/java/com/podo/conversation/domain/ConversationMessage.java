package com.podo.conversation.domain;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entity representing a single message within a conversation.
 */
public class ConversationMessage {

    private final String id;
    private final String role;      // e.g., "user", "assistant"
    private final String content;
    private final LocalDateTime createdAt;

    private ConversationMessage(String id, String role, String content, LocalDateTime createdAt) {
        this.id = id;
        this.role = role;
        this.content = content;
        this.createdAt = createdAt;
    }

    public static ConversationMessage create(String role, String content) {
        return new ConversationMessage(
            UUID.randomUUID().toString(),
            role,
            content,
            LocalDateTime.now()
        );
    }

    public static ConversationMessage reconstitute(String id, String role, String content, LocalDateTime createdAt) {
        return new ConversationMessage(id, role, content, createdAt);
    }

    public String getId() {
        return id;
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

