package com.podo.shared.mediator;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.core.GenericTypeResolver;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Spring-based Mediator implementation.
 * Automatically discovers and routes requests (including domain events) to their handlers.
 */
@Component
@RequiredArgsConstructor
public class SpringMediator implements Mediator {

    private final ApplicationContext applicationContext;
    private final Map<Class<?>, RequestHandler<?, ?>> handlerCache = new ConcurrentHashMap<>();

    @Override
    @SuppressWarnings("unchecked")
    public <TResponse> TResponse send(Request<TResponse> request) {
        RequestHandler<Request<TResponse>, TResponse> handler =
                (RequestHandler<Request<TResponse>, TResponse>) findHandler(request.getClass());

        if (handler == null) {
            throw new IllegalStateException(
                    "No handler found for request: " + request.getClass().getName()
            );
        }

        return handler.handle(request);
    }

    @SuppressWarnings("rawtypes")
    private RequestHandler<?, ?> findHandler(Class<?> requestClass) {
        return handlerCache.computeIfAbsent(requestClass, clazz -> {
            Map<String, RequestHandler> handlers = applicationContext.getBeansOfType(RequestHandler.class);

            for (RequestHandler handler : handlers.values()) {
                Class<?>[] typeArgs = GenericTypeResolver.resolveTypeArguments(
                        handler.getClass(), RequestHandler.class
                );

                if (typeArgs != null && typeArgs.length > 0 && typeArgs[0].equals(requestClass)) {
                    return handler;
                }
            }
            return null;
        });
    }
}
