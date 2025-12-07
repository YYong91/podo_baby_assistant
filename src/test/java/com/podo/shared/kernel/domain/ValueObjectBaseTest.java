package com.podo.shared.kernel.domain;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ValueObjectBaseTest {

    private static class BabyName extends ValueObjectBase {
        private final String first;
        private final String last;

        private BabyName(String first, String last) {
            this.first = first;
            this.last = last;
        }

        @Override
        protected List<Object> equalityComponents() {
            return List.of(first, last);
        }
    }

    @Test
    void valueObjectsWithSameComponents_shouldBeEqual() {
        BabyName left = new BabyName("윤아", "김");
        BabyName right = new BabyName("윤아", "김");

        assertEquals(left, right);
        assertEquals(left.hashCode(), right.hashCode());
    }

    @Test
    void valueObjectsWithDifferentComponents_shouldNotBeEqual() {
        BabyName left = new BabyName("서준", "박");
        BabyName right = new BabyName("민서", "박");

        assertNotEquals(left, right);
    }
}

