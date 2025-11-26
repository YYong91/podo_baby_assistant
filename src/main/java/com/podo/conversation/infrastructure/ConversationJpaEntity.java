package com.podo.conversation.infrastructure;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * JPA entity for Conversation persistence.
 */
@Entity
@Table(name = "conversations")
public class ConversationJpaEntity {

    @Id
    private String id;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime lastUpdatedAt;

    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("createdAt ASC")
    private List<ConversationMessageJpaEntity> messages = new ArrayList<>();

    protected ConversationJpaEntity() {
    }

    public ConversationJpaEntity(String id, LocalDateTime createdAt, LocalDateTime lastUpdatedAt) {
        this.id = id;
        this.createdAt = createdAt;
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public List<ConversationMessageJpaEntity> getMessages() {
        return messages;
    }

    public void setMessages(List<ConversationMessageJpaEntity> messages) {
        this.messages = messages;
    }
}

