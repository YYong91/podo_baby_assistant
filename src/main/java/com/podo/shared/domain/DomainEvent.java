package com.podo.shared.domain;

import java.time.Instant;

/**
 * Marker interface for domain events emitted by aggregates.
 */
public interface DomainEvent {

    /**
     * @return Timestamp describing when the event occurred.
     */
    Instant occurredOn();
}

