package com.podo.conversation.domain;

import com.podo.shared.domain.EntityBase;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Aggregate root for conversation.
 * Contains a list of conversation messages.
 */
public class Conversation extends EntityBase {

    private final List<ConversationMessage> messages;
    private final LocalDateTime createdAt;
    private LocalDateTime lastUpdatedAt;

    private Conversation(UUID id, List<ConversationMessage> messages,
            LocalDateTime createdAt, LocalDateTime lastUpdatedAt) {
        super(id);
        this.messages = new ArrayList<>(messages);
        this.createdAt = createdAt;
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public static Conversation create() {
        LocalDateTime now = LocalDateTime.now();
        return new Conversation(UUID.randomUUID(), new ArrayList<>(), now, now);
    }

    public static Conversation reconstitute(UUID id, List<ConversationMessage> messages,
            LocalDateTime createdAt, LocalDateTime lastUpdatedAt) {
        return new Conversation(id, messages, createdAt, lastUpdatedAt);
    }

    public void addMessage(String role, String content) {
        ConversationMessage message = ConversationMessage.create(role, content);
        this.messages.add(message);
        this.lastUpdatedAt = LocalDateTime.now();
    }

    public List<ConversationMessage> getMessages() {
        return Collections.unmodifiableList(messages);
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getLastUpdatedAt() {
        return lastUpdatedAt;
    }
}

