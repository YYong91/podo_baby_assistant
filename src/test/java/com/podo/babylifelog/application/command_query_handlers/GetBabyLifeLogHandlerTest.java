package com.podo.babylifelog.application.command_query_handlers;

import com.podo.babylifelog.application.queries.GetBabyLifeLogQuery;
import com.podo.babylifelog.application.response.BabyLifeLogResponse;
import com.podo.babylifelog.domain.BabyLifeLogRecord;
import com.podo.babylifelog.domain.BabyLifeLogRepository;
import com.podo.babylifelog.domain.BabyLifeLogType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetBabyLifeLogHandlerTest {

    @Mock
    private BabyLifeLogRepository repository;

    private GetBabyLifeLogHandler handler;

    @BeforeEach
    void setUp() {
        handler = new GetBabyLifeLogHandler(repository);
    }

    @Test
    void handle_shouldReturnResponse_whenRecordExists() {
        BabyLifeLogRecord record = BabyLifeLogRecord.create(
                BabyLifeLogType.DIAPER,
                "기저귀 교체 완료",
                LocalDateTime.of(2025, 11, 27, 14, 15)
        );
        UUID id = Objects.requireNonNull(record.getId());
        when(repository.findById(id)).thenReturn(Optional.of(record));

        BabyLifeLogResponse response = handler.handle(new GetBabyLifeLogQuery(id));

        verify(repository, times(1)).findById(id);
        assertEquals(record.getId(), response.id());
        assertEquals(record.getContent(), response.content());
    }

    @Test
    void handle_shouldThrowException_whenRecordMissing() {
        UUID id = Objects.requireNonNull(UUID.randomUUID());
        when(repository.findById(id)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> handler.handle(new GetBabyLifeLogQuery(id)));

        assertTrue(exception.getMessage().contains(id.toString()));
    }
}

