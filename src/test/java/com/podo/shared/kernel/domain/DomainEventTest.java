package com.podo.shared.kernel.domain;

import com.podo.shared.mediator.Request;
import org.junit.jupiter.api.Test;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class DomainEventTest {

    @Test
    void domainEvent_shouldExtendRequestOfVoid() {
        Type[] interfaces = DomainEvent.class.getGenericInterfaces();

        assertEquals(1, interfaces.length, "DomainEvent는 Request 하나만 상속해야 합니다.");
        ParameterizedType requestType = assertInstanceOf(ParameterizedType.class, interfaces[0]);

        assertEquals(Request.class, requestType.getRawType(), "Request를 상속해야 합니다.");
        assertEquals(Void.class, requestType.getActualTypeArguments()[0], "반환 타입은 Void여야 합니다.");
    }

    @Test
    void domainEvent_shouldRemainMarkerInterface() {
        assertEquals(0, DomainEvent.class.getDeclaredMethods().length,
                "DomainEvent에는 메서드가 없어야 합니다.");
    }
}


