package com.podo.core.orchestrator;

import java.time.Instant;
import java.util.Objects;

/**
 * User utterance enriched with metadata from the gateway.
 */
public record UserUtterance(
        String userId,
        String babyId,
        String deviceId,
        String conversationId,
        String text,
        String locale,
        Instant timestamp
) {

    public UserUtterance {
        conversationId = Objects.requireNonNull(conversationId, "conversationId must not be null");
        text = Objects.requireNonNull(text, "text must not be null");
        timestamp = Objects.requireNonNull(timestamp, "timestamp must not be null");
        locale = locale == null ? "" : locale;
    }

    public boolean hasBabyContext() {
        return babyId != null && !babyId.isBlank();
    }
}
