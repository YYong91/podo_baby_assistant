package com.podo.shared.mediator;

/**
 * Interface for request handlers.
 * @param <TRequest> The type of request to handle
 * @param <TResponse> The type of response to return
 */
public interface RequestHandler<TRequest extends Request<TResponse>, TResponse> {

    /**
     * Handles the given request.
     * @param request The request to handle
     * @return The response
     */
    TResponse handle(TRequest request);
}

