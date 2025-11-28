package com.podo.shared.domain;

import java.util.List;
import java.util.Objects;

/**
 * Base type for immutable value objects.
 * Equality is determined solely by the components returned from {@link #equalityComponents()}.
 */
public abstract class ValueObjectBase {

    protected ValueObjectBase() {
    }

    /**
     * Components that participate in equality comparison.
     * @return immutable list of components
     */
    protected abstract List<Object> equalityComponents();

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ValueObjectBase that = (ValueObjectBase) o;
        return Objects.equals(equalityComponents(), that.equalityComponents());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(equalityComponents().toArray());
    }
}

