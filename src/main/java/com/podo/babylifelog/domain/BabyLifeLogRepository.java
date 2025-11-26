package com.podo.babylifelog.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository port for BabyLifeLog aggregate.
 * This is an interface (port) - implementation is in infrastructure layer.
 */
public interface BabyLifeLogRepository {

    BabyLifeLog save(BabyLifeLog babyLifeLog);

    Optional<BabyLifeLog> findById(Long id);

    List<BabyLifeLog> findAll();

    List<BabyLifeLog> findByLogType(LogType logType);

    List<BabyLifeLog> findByOccurredAtBetween(LocalDateTime start, LocalDateTime end);

    void delete(BabyLifeLog babyLifeLog);
}

