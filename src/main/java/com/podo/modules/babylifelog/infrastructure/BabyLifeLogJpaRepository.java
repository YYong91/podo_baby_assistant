package com.podo.modules.babylifelog.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import com.podo.modules.babylifelog.domain.BabyLifeLogType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Spring Data JPA repository for BabyLifeLogJpaEntity.
 */
public interface BabyLifeLogJpaRepository extends JpaRepository<BabyLifeLogJpaEntity, UUID> {

    List<BabyLifeLogJpaEntity> findByOccurredAtBetween(LocalDateTime start, LocalDateTime end);

    List<BabyLifeLogJpaEntity> findByTypeAndOccurredAtBetween(BabyLifeLogType type, LocalDateTime start, LocalDateTime end);

    Optional<BabyLifeLogJpaEntity> findFirstByType(BabyLifeLogType type);
}
