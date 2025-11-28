package com.podo.shared.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Simple thread-local event collector to coordinate domain events without a full-blown event bus.
 */
public final class DomainEvents {

    private static final ThreadLocal<List<DomainEvent>> EVENTS =
            ThreadLocal.withInitial(ArrayList::new);

    private DomainEvents() {
    }

    public static void add(DomainEvent event) {
        EVENTS.get().add(Objects.requireNonNull(event, "event must not be null"));
    }

    public static void clear() {
        EVENTS.get().clear();
    }

    public static List<DomainEvent> pullEvents() {
        List<DomainEvent> snapshot = Collections.unmodifiableList(new ArrayList<>(EVENTS.get()));
        clear();
        return snapshot;
    }
}

