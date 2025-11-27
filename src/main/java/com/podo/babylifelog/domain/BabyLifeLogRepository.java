package com.podo.babylifelog.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository port for BabyLifeLogRecord aggregate.
 * This is an interface (port) - implementation is in infrastructure layer.
 */
public interface BabyLifeLogRepository {

    BabyLifeLogRecord save(BabyLifeLogRecord record);

    Optional<BabyLifeLogRecord> findById(UUID id);

    List<BabyLifeLogRecord> findAll();

    List<BabyLifeLogRecord> findByOccurredAtBetween(LocalDateTime start, LocalDateTime end);

    List<BabyLifeLogRecord> findByTypeAndOccurredAtBetween(BabyLifeLogType type, LocalDateTime start, LocalDateTime end);

    Optional<BabyLifeLogRecord> findByType(BabyLifeLogType type);

    void delete(BabyLifeLogRecord record);

    void deleteAll();
}
