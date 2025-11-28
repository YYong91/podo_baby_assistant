package com.podo.shared.domain;

import org.junit.jupiter.api.Test;

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
}

