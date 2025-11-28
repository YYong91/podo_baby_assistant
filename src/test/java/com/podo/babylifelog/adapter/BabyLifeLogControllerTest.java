package com.podo.babylifelog.adapter;

import com.podo.babylifelog.application.commands.CreateBabyLifeLogCommand;
import com.podo.babylifelog.application.queries.GetAllBabyLifeLogsQuery;
import com.podo.babylifelog.application.queries.GetBabyLifeLogQuery;
import com.podo.babylifelog.application.response.BabyLifeLogListResponse;
import com.podo.babylifelog.application.response.BabyLifeLogResponse;
import com.podo.babylifelog.domain.BabyLifeLogType;
import com.podo.shared.mediator.Mediator;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BabyLifeLogController.class)
class BabyLifeLogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private Mediator mediator;

    @Test
    void create_shouldDispatchCommandThroughMediator() throws Exception {
        LocalDateTime occurredAt = LocalDateTime.of(2025, 11, 27, 10, 0);
        BabyLifeLogResponse response = new BabyLifeLogResponse(
                UUID.randomUUID(),
                BabyLifeLogType.FEEDING,
                "150ml 분유",
                occurredAt
        );

        when(mediator.send(any(CreateBabyLifeLogCommand.class))).thenReturn(response);

        String payload = """
                {
                  "type": "FEEDING",
                  "content": "150ml 분유",
                  "occurredAt": "2025-11-27T10:00:00"
                }
                """;

        mockMvc.perform(post("/api/baby-life-logs")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(payload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(response.id().toString()))
                .andExpect(jsonPath("$.type").value("FEEDING"))
                .andExpect(jsonPath("$.content").value("150ml 분유"))
                .andExpect(jsonPath("$.occurredAt").value("2025-11-27T10:00:00"));

        ArgumentCaptor<CreateBabyLifeLogCommand> commandCaptor = ArgumentCaptor.forClass(CreateBabyLifeLogCommand.class);
        verify(mediator).send(commandCaptor.capture());

        CreateBabyLifeLogCommand captured = commandCaptor.getValue();
        assertThat(captured.type()).isEqualTo(BabyLifeLogType.FEEDING);
        assertThat(captured.content()).isEqualTo("150ml 분유");
        assertThat(captured.occurredAt()).isEqualTo(occurredAt);
    }

    @Test
    void getAll_shouldSendQueryAndReturnListResponse() throws Exception {
        BabyLifeLogResponse first = new BabyLifeLogResponse(
                UUID.randomUUID(),
                BabyLifeLogType.FEEDING,
                "새벽 수유",
                LocalDateTime.of(2025, 11, 27, 3, 0)
        );
        BabyLifeLogResponse second = new BabyLifeLogResponse(
                UUID.randomUUID(),
                BabyLifeLogType.NAP,
                "아침 낮잠",
                LocalDateTime.of(2025, 11, 27, 9, 30)
        );
        BabyLifeLogListResponse response = new BabyLifeLogListResponse(List.of(first, second));

        when(mediator.send(any(GetAllBabyLifeLogsQuery.class))).thenReturn(response);

        mockMvc.perform(get("/api/baby-life-logs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.logs[0].id").value(first.id().toString()))
                .andExpect(jsonPath("$.logs[1].type").value("NAP"));

        ArgumentCaptor<GetAllBabyLifeLogsQuery> queryCaptor = ArgumentCaptor.forClass(GetAllBabyLifeLogsQuery.class);
        verify(mediator).send(queryCaptor.capture());
        assertThat(queryCaptor.getValue()).isNotNull();
    }

    @Test
    void getById_shouldSendQueryWithPathVariable() throws Exception {
        UUID id = UUID.randomUUID();
        BabyLifeLogResponse response = new BabyLifeLogResponse(
                id,
                BabyLifeLogType.DIAPER,
                "기저귀 교체",
                LocalDateTime.of(2025, 11, 27, 6, 45)
        );

        when(mediator.send(any(GetBabyLifeLogQuery.class))).thenReturn(response);

        mockMvc.perform(get("/api/baby-life-logs/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.type").value("DIAPER"));

        ArgumentCaptor<GetBabyLifeLogQuery> queryCaptor = ArgumentCaptor.forClass(GetBabyLifeLogQuery.class);
        verify(mediator).send(queryCaptor.capture());
        assertThat(queryCaptor.getValue().id()).isEqualTo(id);
    }
}

