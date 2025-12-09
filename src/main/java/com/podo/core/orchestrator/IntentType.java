package com.podo.core.orchestrator;

/**
 * Supported intents recognized by the Brain.
 */
public enum IntentType {
    RECORD_BABY_LOG,
    RECOMMEND_BOOK_AND_ACTIVITY,
    HEALTH_CONCERN,
    DAYCARE_QUESTION,
    SMALL_TALK,
    UNKNOWN;

    /**
     * @return whether this intent needs to call into a module via Mediator.
     */
    public boolean requiresMediatorDispatch() {
        return switch (this) {
            case SMALL_TALK, UNKNOWN -> false;
            default -> true;
        };
    }
}
