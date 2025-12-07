package com.podo.shared.infrastructure;

import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Component;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class NoOpTransientMessagePublisherTest {

    private final NoOpTransientMessagePublisher publisher = new NoOpTransientMessagePublisher();

    @Test
    void inspect_shouldCompleteWithoutSideEffects() {
        assertDoesNotThrow(publisher::inspect);
    }

    @Test
    void sendMessages_shouldCompleteWithoutSideEffects() {
        assertDoesNotThrow(publisher::sendMessages);
    }

    @Test
    void abandonPrepared_shouldCompleteWithoutSideEffects() {
        assertDoesNotThrow(publisher::abandonPrepared);
    }

    @Test
    void componentAnnotation_shouldBePresentForSpringScanning() {
        Component component = NoOpTransientMessagePublisher.class.getAnnotation(Component.class);

        assertNotNull(component, "@Component 애노테이션이 누락되었습니다.");
    }
}


