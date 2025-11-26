package com.podo.babylifelog.infrastructure;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * JPA entity for BabyLifeLog persistence.
 */
@Entity
@Table(name = "baby_life_logs")
public class BabyLifeLogJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String logType;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private LocalDateTime occurredAt;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    protected BabyLifeLogJpaEntity() {
    }

    public BabyLifeLogJpaEntity(Long id, String logType, String description,
                                 LocalDateTime occurredAt, LocalDateTime createdAt) {
        this.id = id;
        this.logType = logType;
        this.description = description;
        this.occurredAt = occurredAt;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getLogType() {
        return logType;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getOccurredAt() {
        return occurredAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}

