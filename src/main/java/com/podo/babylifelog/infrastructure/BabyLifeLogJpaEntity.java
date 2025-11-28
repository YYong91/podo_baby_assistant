package com.podo.babylifelog.infrastructure;

import com.podo.babylifelog.domain.BabyLifeLogType;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * JPA entity for BabyLifeLog persistence.
 */
@Entity
@Table(name = "baby_life_log_record")
public class BabyLifeLogJpaEntity {

    @Id
    @Column(nullable = false, columnDefinition = "uuid")
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BabyLifeLogType type;

    @Column(length = 1000)
    private String content;

    @Column(nullable = false)
    private LocalDateTime occurredAt;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    protected BabyLifeLogJpaEntity() {
    }

    public BabyLifeLogJpaEntity(UUID id, BabyLifeLogType type, String content,
                                 LocalDateTime occurredAt) {
        this.id = id;
        this.type = type;
        this.content = content;
        this.occurredAt = occurredAt;
        // createdAt, updatedAt은 JPA(Hibernate)에서 @CreationTimestamp, @UpdateTimestamp로 자동 관리됩니다.
        // 생성자에서 직접 할당할 필요가 없습니다.
    }

    public UUID getId() {
        return id;
    }

    public BabyLifeLogType getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getOccurredAt() {
        return occurredAt;
    }
}
