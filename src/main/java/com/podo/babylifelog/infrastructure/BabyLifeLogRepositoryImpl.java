package com.podo.babylifelog.infrastructure;

import com.podo.babylifelog.domain.BabyLifeLog;
import com.podo.babylifelog.domain.BabyLifeLogRepository;
import com.podo.babylifelog.domain.LogType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of BabyLifeLogRepository port.
 * Bridges domain repository interface with Spring Data JPA.
 */
@Repository
@RequiredArgsConstructor
public class BabyLifeLogRepositoryImpl implements BabyLifeLogRepository {

    private final BabyLifeLogJpaRepository jpaRepository;

    @Override
    public BabyLifeLog save(BabyLifeLog babyLifeLog) {
        BabyLifeLogJpaEntity entity = toEntity(babyLifeLog);
        BabyLifeLogJpaEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<BabyLifeLog> findById(Long id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<BabyLifeLog> findAll() {
        return jpaRepository.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public List<BabyLifeLog> findByLogType(LogType logType) {
        return jpaRepository.findByLogType(logType.name()).stream().map(this::toDomain).toList();
    }

    @Override
    public List<BabyLifeLog> findByOccurredAtBetween(LocalDateTime start, LocalDateTime end) {
        return jpaRepository.findByOccurredAtBetween(start, end).stream().map(this::toDomain).toList();
    }

    @Override
    public void delete(BabyLifeLog babyLifeLog) {
        jpaRepository.deleteById(babyLifeLog.getId());
    }

    private BabyLifeLogJpaEntity toEntity(BabyLifeLog domain) {
        return new BabyLifeLogJpaEntity(
            domain.getId(),
            domain.getLogType().name(),
            domain.getDescription(),
            domain.getOccurredAt(),
            domain.getCreatedAt()
        );
    }

    private BabyLifeLog toDomain(BabyLifeLogJpaEntity entity) {
        return BabyLifeLog.reconstitute(
            entity.getId(),
            LogType.valueOf(entity.getLogType()),
            entity.getDescription(),
            entity.getOccurredAt(),
            entity.getCreatedAt()
        );
    }
}

