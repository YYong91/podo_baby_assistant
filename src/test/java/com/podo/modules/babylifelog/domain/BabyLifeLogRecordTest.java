package com.podo.modules.babylifelog.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class BabyLifeLogRecordTest {

    @Test
    void create_shouldPopulateFields() {
        BabyLifeLogRecord record = BabyLifeLogRecord.create(
                BabyLifeLogType.FEEDING,
                "Baby had milk",
                LocalDateTime.of(2025, 11, 27, 10, 0)
        );

        assertNotNull(record.getId());
        assertEquals(BabyLifeLogType.FEEDING, record.getType());
        assertEquals("Baby had milk", record.getContent());
        assertEquals(LocalDateTime.of(2025, 11, 27, 10, 0), record.getOccurredAt());
    }

    @Test
    void reconstitute_shouldRestoreAllFields() {
        UUID id = UUID.randomUUID();
        BabyLifeLogType type = BabyLifeLogType.NAP;
        String content = "Morning nap";
        LocalDateTime occurredAt = LocalDateTime.of(2025, 11, 27, 9, 0);

        BabyLifeLogRecord record = BabyLifeLogRecord.reconstitute(
                id, type, content, occurredAt
        );

        assertEquals(id, record.getId());
        assertEquals(type, record.getType());
        assertEquals(content, record.getContent());
        assertEquals(occurredAt, record.getOccurredAt());
    }
}
