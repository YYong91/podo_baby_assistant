package com.podo.messageorchestrator.application.response;

/**
 * Response DTO for processed messages.
 */
public record MessageResponse(
    String messageId,
    String responseContent,
    String targetDomain,  // e.g., "babylifelog", "advice"
    boolean success
) {
    public static MessageResponse success(String messageId, String responseContent, String targetDomain) {
        return new MessageResponse(messageId, responseContent, targetDomain, true);
    }

    public static MessageResponse failure(String messageId, String errorMessage) {
        return new MessageResponse(messageId, errorMessage, null, false);
    }
}

