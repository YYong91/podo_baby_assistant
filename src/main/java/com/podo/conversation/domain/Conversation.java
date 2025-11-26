package com.podo.conversation.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Aggregate root for conversation.
 * Contains a list of conversation messages.
 */
public class Conversation {

    private final String id;
    private final List<ConversationMessage> messages;
    private final LocalDateTime createdAt;
    private LocalDateTime lastUpdatedAt;

    private Conversation(String id, List<ConversationMessage> messages,
                         LocalDateTime createdAt, LocalDateTime lastUpdatedAt) {
        this.id = id;
        this.messages = new ArrayList<>(messages);
        this.createdAt = createdAt;
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public static Conversation create() {
        LocalDateTime now = LocalDateTime.now();
        return new Conversation(UUID.randomUUID().toString(), new ArrayList<>(), now, now);
    }

    public static Conversation reconstitute(String id, List<ConversationMessage> messages,
                                             LocalDateTime createdAt, LocalDateTime lastUpdatedAt) {
        return new Conversation(id, messages, createdAt, lastUpdatedAt);
    }

    public void addMessage(String role, String content) {
        ConversationMessage message = ConversationMessage.create(role, content);
        this.messages.add(message);
        this.lastUpdatedAt = LocalDateTime.now();
    }

    public String getId() {
        return id;
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

