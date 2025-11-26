package com.podo.messageorchestrator.domain;

/**
 * Value object (enum) representing the type of message/intent.
 */
public enum MessageType {
    COMMAND,    // User wants to record something (e.g., "낮잠 잤어")
    QUERY,      // User wants to ask something (e.g., "뒤집기 언제 했어?")
    ADVICE,     // User wants advice (e.g., "이유식 언제 시작해?")
    UNKNOWN     // Unable to determine intent
}

