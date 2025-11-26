package com.podo.advice.domain;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Domain entity representing generated advice.
 */
public class Advice {

    private final String id;
    private final String query;
    private final String content;
    private final LocalDateTime generatedAt;

    private Advice(String id, String query, String content, LocalDateTime generatedAt) {
        this.id = id;
        this.query = query;
        this.content = content;
        this.generatedAt = generatedAt;
    }

    public static Advice create(String query, String content) {
        return new Advice(
            UUID.randomUUID().toString(),
            query,
            content,
            LocalDateTime.now()
        );
    }

    public String getId() {
        return id;
    }

    public String getQuery() {
        return query;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getGeneratedAt() {
        return generatedAt;
    }
}

