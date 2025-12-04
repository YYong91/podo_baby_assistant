package com.podo.babylifelog.infrastructure;

import com.podo.babylifelog.domain.BabyLifeLogRecord;
import com.podo.babylifelog.domain.BabyLifeLogRepository;
import com.podo.babylifelog.domain.BabyLifeLogType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@DataJpaTest
@Import(BabyLifeLogRepositoryImpl.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BabyLifeLogRepositoryImplTest {
    
        
    @Container
    private static final PostgreSQLContainer<?> POSTGRES =
            new PostgreSQLContainer<>("postgres:16-alpine");

    @DynamicPropertySource
    static void overrideDataSourceProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES::getUsername);
        registry.add("spring.datasource.password", POSTGRES::getPassword);
    }

    @Autowired
    BabyLifeLogRepository repository;

    @Autowired
    BabyLifeLogJpaRepository jpaRepository;

    @BeforeEach
    void cleanDatabase() {
        jpaRepository.deleteAll();
    }

    @Test
    void save_shouldPersistDomainEntity() {
        BabyLifeLogRecord toSave = BabyLifeLogRecord.create(
                BabyLifeLogType.FEEDING,
                "200ml 모유",
                LocalDateTime.of(2025, 11, 27, 7, 30)
        );

        BabyLifeLogRecord saved = repository.save(toSave);
        UUID savedId = Objects.requireNonNull(saved.getId(), "id must not be null");
        Optional<BabyLifeLogRecord> loaded = repository.findById(savedId);
        assertThat(savedId).isEqualTo(toSave.getId());
        assertThat(saved.getId()).isEqualTo(toSave.getId());
        assertThat(loaded).isPresent();
        assertThat(loaded.get().getType()).isEqualTo(BabyLifeLogType.FEEDING);
        assertThat(loaded.get().getContent()).isEqualTo("200ml 모유");
        assertThat(loaded.get().getOccurredAt()).isEqualTo(LocalDateTime.of(2025, 11, 27, 7, 30));
    }

    @Test
    void findByOccurredAtBetween_shouldReturnFilteredResults() {
        BabyLifeLogRecord first = BabyLifeLogRecord.create(
                BabyLifeLogType.NAP,
                "40분 낮잠",
                LocalDateTime.of(2025, 11, 27, 9, 0)
        );
        BabyLifeLogRecord second = BabyLifeLogRecord.create(
                BabyLifeLogType.DIAPER,
                "기저귀 교체",
                LocalDateTime.of(2025, 11, 27, 11, 0)
        );
        BabyLifeLogRecord third = BabyLifeLogRecord.create(
                BabyLifeLogType.PLAY,
                "책 읽기",
                LocalDateTime.of(2025, 11, 27, 15, 0)
        );

        repository.save(first);
        repository.save(second);
        repository.save(third);

        List<BabyLifeLogRecord> morningLogs = repository.findByOccurredAtBetween(
                LocalDateTime.of(2025, 11, 27, 8, 0),
                LocalDateTime.of(2025, 11, 27, 12, 0)
        );

        assertThat(morningLogs)
                .extracting(BabyLifeLogRecord::getId)
                .containsExactlyInAnyOrder(first.getId(), second.getId());
    }
}

