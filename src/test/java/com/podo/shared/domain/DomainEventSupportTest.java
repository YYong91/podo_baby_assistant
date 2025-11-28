package com.podo.shared.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DomainEventSupportTest {

    @AfterEach
    void tearDown() {
        DomainEvents.clear();
    }

    @Test
    void domainEventBase_shouldCaptureTimestamp() {
        Instant before = Instant.now();
        TestDomainEvent event = new TestDomainEvent("민준");
        Instant after = Instant.now();

        assertFalse(event.occurredOn().isBefore(before));
        assertFalse(event.occurredOn().isAfter(after));
    }

    @Test
    void domainEvents_shouldCollectAndClear() {
        TestDomainEvent first = new TestDomainEvent("하윤");
        TestDomainEvent second = new TestDomainEvent("시우");

        DomainEvents.add(first);
        DomainEvents.add(second);

        List<DomainEvent> collected = DomainEvents.pullEvents();

        assertEquals(List.of(first, second), collected);
        assertTrue(DomainEvents.pullEvents().isEmpty(), "pullEvents should clear the buffer");
    }

    private static class TestDomainEvent extends DomainEventBase {
        private final String name;

        private TestDomainEvent(String name) {
            this.name = name;
        }

        @SuppressWarnings("unused")
        String getName() {
            return name;
        }
    }
}

