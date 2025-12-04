package com.podo.shared.mediator;

/**
 * Mediator interface for dispatching requests to their handlers.
 * Domain events also implement {@link Request}, so they are dispatched via {@code send()}.
 */
public interface Mediator {

    /**
     * Sends a request (or domain event) to its handler and returns the response.
     * For domain events the response type is {@code Void}.
     *
     * @param request The request to send
     * @param <TResponse> The response type
     * @return The response from the handler (null for Void)
     */
    <TResponse> TResponse send(Request<TResponse> request);
}
