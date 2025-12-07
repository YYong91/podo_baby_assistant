package com.podo.shared.infrastructure;

import com.podo.shared.kernel.application.TransientMessagePublisher;
import com.podo.shared.kernel.application.UnitOfWork;
import com.podo.shared.kernel.domain.DomainEvent;
import com.podo.shared.kernel.domain.EntityBase;
import com.podo.shared.mediator.Mediator;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Spring/JPA-based UnitOfWork.
 * <p>
 * Mirrors the Python implementation:
 * <ul>
 *   <li>dispatchEvents - publishes domain events via Mediator without DB commit</li>
 *   <li>saveChanges   - dispatches events, inspects messages, commits DB, then sends messages</li>
 *   <li>reset         - rollbacks the current transaction and abandons prepared messages</li>
 * </ul>
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SpringUnitOfWork implements UnitOfWork {

    private final EntityManager entityManager;
    private final Mediator mediator;
    private final TransientMessagePublisher messagePublisher;

    @Override
    public void dispatchEvents() {
        List<DomainEvent> events = collectEventsFromEntities();
        for (DomainEvent event : events) {
            mediator.send(event);  // DomainEvent extends Request<Void>
        }
    }

    @Override
    public void saveChanges() {
        dispatchEvents();

        try {
            messagePublisher.inspect();
        } catch (Exception e) {
            log.error("Message publisher inspection failed", e);
            throw e;
        }

        try {
            entityManager.flush();
        } catch (Exception e) {
            reset();
            log.error("EntityManager flush failed", e);
            throw e;
        }

        try {
            messagePublisher.sendMessages();
        } catch (Exception e) {
            log.error("Message publisher sendMessages failed. DB already committed!", e);
            throw e;
        }
    }

    @Override
    public void reset() {
        messagePublisher.abandonPrepared();
        entityManager.clear();
    }

    private List<DomainEvent> collectEventsFromEntities() {
        List<DomainEvent> events = new ArrayList<>();

        // Iterate entities in persistence context
        for (Object entity : getManagedEntities()) {
            if (entity instanceof EntityBase aggregate) {
                events.addAll(aggregate.pullDomainEvents());
            }
        }

        return events;
    }

    /**
     * Extracts managed entities from the current persistence context.
     * Uses Hibernate API if available, otherwise returns empty (fallback).
     */
    protected Iterable<Object> getManagedEntities() {
        try {
            org.hibernate.Session session = entityManager.unwrap(org.hibernate.Session.class);
            org.hibernate.engine.spi.PersistenceContext pc =
                    ((org.hibernate.engine.spi.SessionImplementor) session).getPersistenceContext();

            List<Object> entities = new ArrayList<>();
            pc.getEntitiesByKey().values().forEach(entities::add);
            return entities;
        } catch (Exception e) {
            log.warn("Could not access Hibernate persistence context; skipping entity event collection", e);
            return List.of();
        }
    }
}
