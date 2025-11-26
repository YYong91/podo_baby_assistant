package com.podo.messageorchestrator.domain;

/**
 * Domain service interface for routing messages to appropriate handlers.
 * This is a port - implementation will be in infrastructure or application layer.
 */
public interface MessageRouter {

    /**
     * Routes a message to the appropriate domain handler.
     * @param message The message to route
     * @return Routing result containing target domain and parsed data
     */
    RoutingResult route(Message message);

    /**
     * Result of message routing.
     */
    record RoutingResult(
        String targetDomain,    // e.g., "babylifelog", "conversation", "advice"
        MessageType messageType,
        Object parsedData       // Parsed intent data
    ) {}
}

