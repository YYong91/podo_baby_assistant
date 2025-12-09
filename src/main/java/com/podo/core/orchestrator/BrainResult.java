package com.podo.core.orchestrator;

import java.util.Map;
import java.util.Objects;

/**
 * Result returned from the Brain model containing its best guess.
 */
public record BrainResult(
        IntentType intent,
        Map<String, Object> slots,
        boolean isAboutBaby,
        boolean shouldStoreConversation,
        String replyDraft
) {

    public BrainResult {
        IntentType safeIntent = intent == null ? IntentType.UNKNOWN : intent;
        intent = safeIntent;
        slots = slots == null ? Map.of() : Map.copyOf(slots);
        replyDraft = replyDraft == null ? "" : replyDraft;
    }

    /**
     * Convenience accessor returning {@link IntentType#UNKNOWN} when the model could not decide.
     */
    public IntentType resolvedIntent() {
        return Objects.requireNonNullElse(intent, IntentType.UNKNOWN);
    }
}
