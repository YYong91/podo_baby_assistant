package com.podo.shared.infrastructure;

import com.podo.shared.application.TransientMessagePublisher;
import com.podo.shared.domain.DomainEvent;
import com.podo.shared.domain.EntityBase;
import com.podo.shared.mediator.Mediator;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.engine.spi.EntityKey;
import org.hibernate.engine.spi.PersistenceContext;
import org.hibernate.engine.spi.SessionImplementor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;
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
    @Mock
    private Session hibernateSession;
    @Mock
    private PersistenceContext persistenceContext;

    private SpringUnitOfWork unitOfWork;

    @BeforeEach
    void setUp() {
        unitOfWork = new SpringUnitOfWork(entityManager, mediator, messagePublisher);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Test
    void dispatchEvents_shouldPublishEventsFromManagedEntities() {
        TestEntity entity = new TestEntity();
        TestDomainEvent event = new TestDomainEvent("테스트");
        entity.raiseEvent(event);

        Map entitiesMap = new HashMap<>();
        entitiesMap.put(mock(EntityKey.class), entity);

        when(entityManager.unwrap(Session.class)).thenReturn(hibernateSession);
        when(((SessionImplementor) hibernateSession).getPersistenceContext()).thenReturn(persistenceContext);
        when(persistenceContext.getEntitiesByKey()).thenReturn(entitiesMap);

        unitOfWork.dispatchEvents();

        verify(mediator).send(event);
        assertTrue(entity.pullDomainEvents().isEmpty(), "이벤트가 소비되어야 함");
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Test
    void saveChanges_shouldDispatchEvents_thenFlush_thenSendMessages() {
        Map entitiesMap = new HashMap<>();

        when(entityManager.unwrap(Session.class)).thenReturn(hibernateSession);
        when(((SessionImplementor) hibernateSession).getPersistenceContext()).thenReturn(persistenceContext);
        when(persistenceContext.getEntitiesByKey()).thenReturn(entitiesMap);

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
}
