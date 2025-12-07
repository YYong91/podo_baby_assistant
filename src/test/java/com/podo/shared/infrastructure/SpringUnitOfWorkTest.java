package com.podo.shared.infrastructure;

import com.podo.shared.kernel.application.TransientMessagePublisher;
import com.podo.shared.kernel.domain.DomainEvent;
import com.podo.shared.kernel.domain.EntityBase;
import com.podo.shared.mediator.Mediator;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpringUnitOfWorkTest {

    @Mock
    private EntityManager entityManager;
    @Mock
    private Mediator mediator;
    @Mock
    private TransientMessagePublisher messagePublisher;
    private SpringUnitOfWork unitOfWork;

    @BeforeEach
    void setUp() {
        unitOfWork = new TestableSpringUnitOfWork(entityManager, mediator, messagePublisher);
    }

    @Test
    void dispatchEvents_shouldPublishEventsFromManagedEntities() {
        TestEntity entity = new TestEntity();
        TestDomainEvent event = new TestDomainEvent("테스트");
        entity.raiseEvent(event);

        ((TestableSpringUnitOfWork) unitOfWork).setManagedEntities(List.of(entity));
        unitOfWork.dispatchEvents();

        verify(mediator).send(event);
        assertTrue(entity.pullDomainEvents().isEmpty(), "이벤트가 소비되어야 함");
    }

    @Test
    void saveChanges_shouldDispatchEvents_thenFlush_thenSendMessages() {
        ((TestableSpringUnitOfWork) unitOfWork).setManagedEntities(List.of());
        unitOfWork.saveChanges();

        var inOrder = inOrder(messagePublisher, entityManager);
        inOrder.verify(messagePublisher).inspect();
        inOrder.verify(entityManager).flush();
        inOrder.verify(messagePublisher).sendMessages();
    }

    @Test
    void reset_shouldAbandonMessagesAndClearEntityManager() {
        unitOfWork.reset();

        verify(messagePublisher).abandonPrepared();
        verify(entityManager).clear();
    }

    // --- test fixtures ---

    private static class TestEntity extends EntityBase {
        TestEntity() {
            super(UUID.randomUUID());
        }

        void raiseEvent(DomainEvent event) {
            addDomainEvent(event);
        }
    }

    private static class TestDomainEvent implements DomainEvent {
        @SuppressWarnings("unused")
        private final String message;

        TestDomainEvent(String message) {
            this.message = message;
        }
    }

    private static class TestableSpringUnitOfWork extends SpringUnitOfWork {

        private Iterable<Object> managedEntities = List.of();

        TestableSpringUnitOfWork(EntityManager entityManager, Mediator mediator,
                TransientMessagePublisher messagePublisher) {
            super(entityManager, mediator, messagePublisher);
        }

        void setManagedEntities(Iterable<Object> entities) {
            this.managedEntities = entities;
        }

        @Override
        protected Iterable<Object> getManagedEntities() {
            return managedEntities;
        }
    }
}
