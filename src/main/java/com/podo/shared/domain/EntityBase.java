package com.podo.shared.domain;

import java.util.Objects;
import java.util.UUID;

import org.springframework.lang.NonNull;

/**
 * Abstract base class for all domain entities.
 * Provides UUID-based identity and equality semantics.
 */
public abstract class EntityBase {

    protected UUID id;

    protected EntityBase() {
        this.id = UUID.randomUUID();
    }

    protected EntityBase(UUID id) {
        this.id = id;
    }

    @SuppressWarnings("null")
    @NonNull
    public UUID getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityBase that = (EntityBase) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

