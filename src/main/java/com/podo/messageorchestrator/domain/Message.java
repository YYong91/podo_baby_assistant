package com.podo.messageorchestrator.domain;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Domain entity representing an incoming user message.
 * Pure domain object with no framework dependencies.
 */
public class Message {

    private final String id;
    private final String content;
    private final MessageType type;
    private final LocalDateTime receivedAt;

    private Message(String id, String content, MessageType type, LocalDateTime receivedAt) {
        this.id = id;
        this.content = content;
        this.type = type;
        this.receivedAt = receivedAt;
    }

    public static Message create(String content, MessageType type) {
        return new Message(
            UUID.randomUUID().toString(),
            content,
            type,
            LocalDateTime.now()
        );
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public MessageType getType() {
        return type;
    }

    public LocalDateTime getReceivedAt() {
        return receivedAt;
    }
}

