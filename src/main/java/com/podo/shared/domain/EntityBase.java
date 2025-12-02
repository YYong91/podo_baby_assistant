package com.podo.shared.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Abstract base class for all domain entities.
 * Provides UUID-based identity and equality semantics.
 * Maintains a list of domain events to be dispatched via UnitOfWork.
 */
public abstract class EntityBase {

    protected UUID id;
    private final List<DomainEvent> domainEvents = new ArrayList<>();

    protected EntityBase() {
        this.id = UUID.randomUUID();
    }

    protected EntityBase(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    /**
     * Raises a domain event. Events are stored until UnitOfWork collects them.
     */
    protected void addDomainEvent(DomainEvent event) {
        domainEvents.add(Objects.requireNonNull(event, "event must not be null"));
    }

    /**
     * Returns an unmodifiable view of pending domain events.
     */
    public List<DomainEvent> getDomainEvents() {
        return Collections.unmodifiableList(domainEvents);
    }

    /**
     * Returns and clears pending domain events (consumed by UnitOfWork).
     */
    public List<DomainEvent> pullDomainEvents() {
        List<DomainEvent> copy = new ArrayList<>(domainEvents);
        domainEvents.clear();
        return copy;
    }

    /**
     * Clears all pending domain events without returning them.
     */
    protected void clearDomainEvents() {
        domainEvents.clear();
    }

    public boolean hasDomainEvent(Class<? extends DomainEvent> eventClass) {
        return domainEvents.stream().anyMatch(e -> e.getClass().equals(eventClass));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityBase that = (EntityBase) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
