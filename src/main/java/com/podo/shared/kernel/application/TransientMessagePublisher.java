package com.podo.shared.kernel.application;

/**
 * Port for publishing transient messages (MQ, email, etc.) after DB commit.
 * Implementations buffer messages during the transaction and send them in a batch.
 */
public interface TransientMessagePublisher {

    /**
     * Prepares messages for sending. Called before DB commit to validate payloads.
     * Throws if any message is malformed, to prevent commit+send mismatch.
     */
    void inspect();

    /**
     * Actually sends the prepared messages. Called after DB commit.
     */
    void sendMessages();

    /**
     * Discards prepared messages on transaction failure.
     */
    void abandonPrepared();
}

