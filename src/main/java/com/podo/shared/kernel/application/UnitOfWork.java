package com.podo.shared.kernel.application;

/**
 * Coordinates transactional work and domain event publication.
 * Inspired by the Python IUnitOfWork design.
 */
public interface UnitOfWork {

    /**
     * Dispatches domain events without committing the transaction.
     * Useful inside domain-event handlers where commit will happen later.
     */
    void dispatchEvents();

    /**
     * Commits the current work: dispatches events, inspects outgoing messages,
     * flushes to DB, then sends external messages.
     */
    void saveChanges();

    /**
     * Rollback the current transaction and abandon prepared messages.
     */
    void reset();
}
