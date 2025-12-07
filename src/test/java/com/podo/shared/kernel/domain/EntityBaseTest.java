package com.podo.shared.kernel.domain;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class EntityBaseTest {

    private static class TestEntity extends EntityBase {
        TestEntity() {
            super();
        }

        TestEntity(UUID id) {
            super(id);
        }
    }

    private static class EventEntity extends EntityBase {
        void raise(String name) {
            addDomainEvent(new TestDomainEvent(name));
        }
    }

    private static class TestDomainEvent implements DomainEvent {
        @SuppressWarnings("unused")
        private final String name;

        private TestDomainEvent(String name) {
            this.name = name;
        }
    }

    @Test
    void entityBase_shouldAssignRandomUUID_whenCreatedWithNoArgs() {
        TestEntity entity = new TestEntity();

        assertNotNull(entity.getId());
    }

    @Test
    void entityBase_shouldUseProvidedUUID_whenCreatedWithId() {
        UUID id = UUID.randomUUID();
        TestEntity entity = new TestEntity(id);

        assertEquals(id, entity.getId());
    }

    @Test
    void entityBase_shouldBeEqual_whenIdsMatch() {
        UUID id = UUID.randomUUID();
        TestEntity entity1 = new TestEntity(id);
        TestEntity entity2 = new TestEntity(id);

        assertEquals(entity1, entity2);
        assertEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    void entityBase_shouldNotBeEqual_whenIdsDiffer() {
        TestEntity entity1 = new TestEntity();
        TestEntity entity2 = new TestEntity();

        assertNotEquals(entity1, entity2);
    }

    @Test
    void addDomainEvent_shouldStoreInEntity() {
        EventEntity entity = new EventEntity();

        entity.raise("첫 번째 이벤트");
        entity.raise("두 번째 이벤트");

        assertEquals(2, entity.getDomainEvents().size());
    }

    @Test
    void pullDomainEvents_shouldReturnAndClearEvents() {
        EventEntity entity = new EventEntity();
        entity.raise("이벤트");

        List<DomainEvent> pulled = entity.pullDomainEvents();

        assertEquals(1, pulled.size());
        assertTrue(entity.getDomainEvents().isEmpty(), "pull 이후 비어 있어야 함");
    }

    @Test
    void hasDomainEvent_shouldReturnTrueIfEventExists() {
        EventEntity entity = new EventEntity();
        entity.raise("체크");

        assertTrue(entity.hasDomainEvent(TestDomainEvent.class));
    }

    @Test
    void clearDomainEvents_shouldRemoveAllEvents() {
        EventEntity entity = new EventEntity();
        entity.raise("지울 이벤트");

        entity.clearDomainEvents();

        assertTrue(entity.getDomainEvents().isEmpty());
    }
}
