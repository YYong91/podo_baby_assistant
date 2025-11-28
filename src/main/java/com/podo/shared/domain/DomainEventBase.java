package com.podo.shared.domain;

import java.time.Instant;
import java.util.Objects;

/**
 * Convenience base class for domain events that automatically captures {@link Instant#now()}.
 */
public abstract class DomainEventBase implements DomainEvent {

    private final Instant occurredOn;

    protected DomainEventBase() {
        this(Instant.now());
    }

    protected DomainEventBase(Instant occurredOn) {
        this.occurredOn = Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }

    @Override
    public final Instant occurredOn() {
        return occurredOn;
    }
}

