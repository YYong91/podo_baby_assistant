package com.podo.babylifelog.application.command_query_handlers;

import com.podo.babylifelog.application.commands.CreateBabyLifeLogCommand;
import com.podo.babylifelog.application.response.BabyLifeLogResponse;
import com.podo.babylifelog.domain.BabyLifeLogRecord;
import com.podo.babylifelog.domain.BabyLifeLogRepository;
import com.podo.babylifelog.domain.BabyLifeLogType;
import com.podo.shared.domain.UnitOfWork;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateBabyLifeLogHandlerTest {

    @Mock
    private BabyLifeLogRepository repository;

    @Mock
    private UnitOfWork unitOfWork;

    private CreateBabyLifeLogHandler handler;

    @BeforeEach
    void setUp() {
        handler = new CreateBabyLifeLogHandler(repository, unitOfWork);
    }

    @Test
    void handle_shouldPersistDomainEntity_andReturnResponse() {
        LocalDateTime occurredAt = LocalDateTime.of(2025, 11, 27, 10, 0);
        CreateBabyLifeLogCommand command = new CreateBabyLifeLogCommand(
                BabyLifeLogType.FEEDING,
                "150ml 분유",
                occurredAt
        );

        when(repository.save(any(BabyLifeLogRecord.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        BabyLifeLogResponse response = handler.handle(command);

        ArgumentCaptor<BabyLifeLogRecord> recordCaptor = ArgumentCaptor.forClass(BabyLifeLogRecord.class);
        verify(repository, times(1)).save(recordCaptor.capture());

        BabyLifeLogRecord savedRecord = recordCaptor.getValue();
        assertEquals(BabyLifeLogType.FEEDING, savedRecord.getType());
        assertEquals("150ml 분유", savedRecord.getContent());
        assertEquals(occurredAt, savedRecord.getOccurredAt());
        assertEquals(savedRecord.getId(), response.id());
        assertEquals(savedRecord.getContent(), response.content());
    }
}

