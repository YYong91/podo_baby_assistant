package com.podo.babylifelog.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Spring Data JPA repository for BabyLifeLogJpaEntity.
 */
@Repository
public interface BabyLifeLogJpaRepository extends JpaRepository<BabyLifeLogJpaEntity, Long> {

    List<BabyLifeLogJpaEntity> findByLogType(String logType);

    List<BabyLifeLogJpaEntity> findByOccurredAtBetween(LocalDateTime start, LocalDateTime end);
}

