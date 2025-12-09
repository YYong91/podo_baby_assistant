package com.podo.core.orchestrator;

import java.util.Objects;

/**
 * Final DTO returned to the API adapter.
 */
public record OrchestratorResult(
        String conversationId,
        String replyText,
        IntentType intent,
        boolean isAboutBaby,
        boolean shouldStoreConversation
) {

    public OrchestratorResult {
        conversationId = Objects.requireNonNull(conversationId, "conversationId must not be null");
        intent = intent == null ? IntentType.UNKNOWN : intent;
        replyText = replyText == null ? "" : replyText.trim();
    }
}
