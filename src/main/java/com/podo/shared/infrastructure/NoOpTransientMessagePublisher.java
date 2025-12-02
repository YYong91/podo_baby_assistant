package com.podo.shared.infrastructure;

import org.springframework.stereotype.Component;

import com.podo.shared.application.TransientMessagePublisher;

/**
 * No-op implementation of {@link TransientMessagePublisher}.
 * Useful until a real MQ/email integration is added.
 */
@Component
public class NoOpTransientMessagePublisher implements TransientMessagePublisher {

    @Override
    public void inspect() {
        // no-op
    }

    @Override
    public void sendMessages() {
        // no-op
    }

    @Override
    public void abandonPrepared() {
        // no-op
    }
}

