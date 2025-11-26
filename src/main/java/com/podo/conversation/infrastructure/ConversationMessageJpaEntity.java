package com.podo.conversation.infrastructure;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * JPA entity for ConversationMessage persistence.
 */
@Entity
@Table(name = "conversation_messages")
public class ConversationMessageJpaEntity {

    @Id
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conversation_id", nullable = false)
    private ConversationJpaEntity conversation;

    @Column(nullable = false)
    private String role;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    protected ConversationMessageJpaEntity() {
    }

    public ConversationMessageJpaEntity(String id, ConversationJpaEntity conversation,
                                         String role, String content, LocalDateTime createdAt) {
        this.id = id;
        this.conversation = conversation;
        this.role = role;
        this.content = content;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public ConversationJpaEntity getConversation() {
        return conversation;
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

