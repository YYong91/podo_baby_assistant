package com.podo.modules.babylifelog.infrastructure;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.podo.modules.babylifelog.domain.BabyLifeLogRecord;
import com.podo.modules.babylifelog.domain.BabyLifeLogRepository;
import com.podo.modules.babylifelog.domain.BabyLifeLogType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementation of BabyLifeLogRepository port.
 * Bridges domain repository interface with Spring Data JPA.
 */
@Repository
public class BabyLifeLogRepositoryImpl implements BabyLifeLogRepository {

    private final BabyLifeLogJpaRepository jpaRepository;

    public BabyLifeLogRepositoryImpl(BabyLifeLogJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public BabyLifeLogRecord save(BabyLifeLogRecord record) {
        BabyLifeLogJpaEntity entity = toEntity(record);
        BabyLifeLogJpaEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<BabyLifeLogRecord> findById(@NonNull UUID id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<BabyLifeLogRecord> findAll() {
        return jpaRepository.findAll().stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public List<BabyLifeLogRecord> findByOccurredAtBetween(LocalDateTime start, LocalDateTime end) {
        return jpaRepository.findByOccurredAtBetween(start, end).stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public List<BabyLifeLogRecord> findByTypeAndOccurredAtBetween(BabyLifeLogType type, LocalDateTime start, LocalDateTime end) {
        return jpaRepository.findByTypeAndOccurredAtBetween(type, start, end).stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public Optional<BabyLifeLogRecord> findByType(BabyLifeLogType type) {
        return jpaRepository.findFirstByType(type).map(this::toDomain);
    }

    @Override
    public void delete(BabyLifeLogRecord record) {
        UUID id = Objects.requireNonNull(record.getId(), "record id must not be null");
        jpaRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        jpaRepository.deleteAll();
    }
    
    @NonNull
    private BabyLifeLogJpaEntity toEntity(BabyLifeLogRecord record) {
        return new BabyLifeLogJpaEntity(
                record.getId(),
                record.getType(),
                record.getContent(),
                record.getOccurredAt()
        );
    }

    private BabyLifeLogRecord toDomain(BabyLifeLogJpaEntity entity) {
        return BabyLifeLogRecord.reconstitute(
                entity.getId(),
                entity.getType(),
                entity.getContent(),
                entity.getOccurredAt()
        );
    }
}
