package com.podo.shared.kernel.domain;

import com.podo.shared.mediator.Request;

/**
 * Marker interface for domain events emitted by aggregates.
 * Extends {@link Request} so events can be dispatched via {@code mediator.send(event)}.
 */
public interface DomainEvent extends Request<Void> {
}
