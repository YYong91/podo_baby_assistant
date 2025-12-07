package com.podo.shared.kernel.application;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TransientMessagePublisherContractTest {

    @Test
    void interface_shouldDeclareLifecycleMethodsReturningVoid() {
        Method[] methods = TransientMessagePublisher.class.getDeclaredMethods();

        assertEquals(3, methods.length, "inspect/sendMessages/abandonPrepared 세 메서드만 존재해야 합니다.");

        Set<String> methodNames = Arrays.stream(methods)
                .map(Method::getName)
                .collect(Collectors.toSet());

        assertEquals(
                Set.of("inspect", "sendMessages", "abandonPrepared"),
                methodNames,
                "메서드 이름이 계약과 일치하지 않습니다."
        );

        assertTrue(Arrays.stream(methods).allMatch(method -> method.getReturnType().equals(void.class)),
                "모든 메서드는 void를 반환해야 합니다.");

        assertTrue(Arrays.stream(methods).allMatch(method -> method.getParameterCount() == 0),
                "메서드는 인자를 받지 않는 순수 라이프사이클 동작이어야 합니다.");
    }
}


