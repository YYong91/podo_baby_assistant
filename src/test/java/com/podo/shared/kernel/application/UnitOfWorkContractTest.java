package com.podo.shared.kernel.application;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UnitOfWorkContractTest {

    @Test
    void interface_shouldExposeTransactionalLifecycle() {
        Method[] methods = UnitOfWork.class.getDeclaredMethods();

        assertEquals(3, methods.length, "dispatchEvents/saveChanges/reset 세 메서드만 선언되어야 합니다.");

        Set<String> methodNames = Arrays.stream(methods)
                .map(Method::getName)
                .collect(Collectors.toSet());

        assertEquals(
                Set.of("dispatchEvents", "saveChanges", "reset"),
                methodNames,
                "UnitOfWork의 공개 API가 변경되었습니다."
        );

        assertTrue(Arrays.stream(methods).allMatch(method -> method.getReturnType().equals(void.class)),
                "모든 메서드는 void를 반환해야 합니다.");

        assertTrue(Arrays.stream(methods).allMatch(method -> method.getParameterCount() == 0),
                "메서드는 매개변수를 받지 않아야 합니다.");
    }
}


