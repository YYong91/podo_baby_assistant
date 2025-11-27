package com.podo.babylifelog.infrastructure;

import com.podo.babylifelog.domain.BabyLifeLogType;
import org.springframework.data.jpa.repository.JpaRepository;

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
