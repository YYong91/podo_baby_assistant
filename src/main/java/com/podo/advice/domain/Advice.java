package com.podo.advice.domain;

import com.podo.shared.domain.EntityBase;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Domain entity representing generated advice.
 */
public class Advice extends EntityBase {

    private final String query;
    private final String content;
    private final LocalDateTime generatedAt;

    private Advice(UUID id, String query, String content, LocalDateTime generatedAt) {
        super(id);
        this.query = query;
        this.content = content;
        this.generatedAt = generatedAt;
    }

    public static Advice create(String query, String content) {
        return new Advice(
            UUID.randomUUID(),
            query,
            content,
            LocalDateTime.now()
        );
    }

    public static Advice reconstitute(UUID id, String query, String content, LocalDateTime generatedAt) {
        return new Advice(id, query, content, generatedAt);
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

