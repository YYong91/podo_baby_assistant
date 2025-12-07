package com.podo.modules.babylifelog.application.command_query_handlers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.podo.modules.babylifelog.application.queries.GetAllBabyLifeLogsQuery;
import com.podo.modules.babylifelog.application.response.BabyLifeLogListResponse;
import com.podo.modules.babylifelog.domain.BabyLifeLogRecord;
import com.podo.modules.babylifelog.domain.BabyLifeLogRepository;
import com.podo.modules.babylifelog.domain.BabyLifeLogType;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetAllBabyLifeLogsHandlerTest {

    @Mock
    private BabyLifeLogRepository repository;

    private GetAllBabyLifeLogsHandler handler;

    @BeforeEach
    void setUp() {
        handler = new GetAllBabyLifeLogsHandler(repository);
    }

    @Test
    void handle_shouldReturnMappedResponses() {
        BabyLifeLogRecord first = BabyLifeLogRecord.create(
                BabyLifeLogType.FEEDING,
                "140ml 분유",
                LocalDateTime.of(2025, 11, 27, 8, 0)
        );
        BabyLifeLogRecord second = BabyLifeLogRecord.create(
                BabyLifeLogType.NAP,
                "1시간 낮잠",
                LocalDateTime.of(2025, 11, 27, 12, 30)
        );

        when(repository.findAll()).thenReturn(List.of(first, second));

        BabyLifeLogListResponse response = handler.handle(new GetAllBabyLifeLogsQuery());

        verify(repository, times(1)).findAll();
        assertEquals(2, response.logs().size());
        assertEquals(first.getId(), response.logs().get(0).id());
        assertEquals(second.getType(), response.logs().get(1).type());
    }
}

