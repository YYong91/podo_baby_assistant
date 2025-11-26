package com.podo.shared.mediator;

/**
 * Mediator interface for dispatching requests to their handlers.
 */
public interface Mediator {

    /**
     * Sends a request to its handler and returns the response.
     * @param request The request to send
     * @param <TResponse> The response type
     * @return The response from the handler
     */
    <TResponse> TResponse send(Request<TResponse> request);
}

