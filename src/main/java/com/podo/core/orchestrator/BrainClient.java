package com.podo.core.orchestrator;

/**
 * Gateway to the conversational AI (Brain).
 */
@FunctionalInterface
public interface BrainClient {

    /**
     * Analyzes the user utterance and returns the inferred intent plus metadata.
     *
     * @param utterance user-provided utterance metadata
     * @return inference result from the brain service
     */
    BrainResult analyze(UserUtterance utterance);
}
