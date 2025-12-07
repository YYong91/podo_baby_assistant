package com.podo.shared.mediator;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.TypeVariable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MediatorContractTest {

    @Test
    void request_shouldRemainMarkerInterface() {
        assertEquals(0, Request.class.getDeclaredMethods().length, "Request는 마커 인터페이스여야 합니다.");
    }

    @Test
    @SuppressWarnings("rawtypes")
    void requestHandler_shouldRequireRequestSubtype() {
        TypeVariable<Class<RequestHandler>>[] typeParameters = RequestHandler.class.getTypeParameters();

        assertEquals(2, typeParameters.length, "TRequest, TResponse 두 개의 타입 파라미터가 필요합니다.");

        TypeVariable<Class<RequestHandler>> requestParam = typeParameters[0];
        assertEquals("TRequest", requestParam.getName());
        ParameterizedType bound = assertInstanceOf(ParameterizedType.class, requestParam.getBounds()[0]);
        assertEquals(Request.class, bound.getRawType(), "TRequest는 Request를 상속해야 합니다.");

        TypeVariable<Class<RequestHandler>> responseParam = typeParameters[1];
        assertEquals("TResponse", responseParam.getName());
        assertEquals(Object.class, responseParam.getBounds()[0], "TResponse는 제한이 없어야 합니다.");
    }

    @Test
    void handleMethod_shouldMatchGenericSignature() throws NoSuchMethodException {
        Method handle = RequestHandler.class.getDeclaredMethod("handle", Request.class);

        assertEquals("handle", handle.getName());
        assertEquals(1, handle.getParameterCount());
        assertEquals(Object.class, handle.getReturnType(),
                "제네릭 반환형은 타입 이레이저 후 Object여야 합니다.");
    }

    @Test
    void mediatorSend_shouldExposeGenericReturnType() throws NoSuchMethodException {
        Method send = Mediator.class.getDeclaredMethod("send", Request.class);

        assertEquals(1, send.getTypeParameters().length, "TResponse 제네릭이 필요합니다.");
        TypeVariable<Method> responseType = send.getTypeParameters()[0];
        assertEquals("TResponse", responseType.getName());

        assertTrue(send.getGenericReturnType() instanceof TypeVariable<?>,
                "반환형은 제네릭 타입이어야 합니다.");

        ParameterizedType parameterType = assertInstanceOf(ParameterizedType.class, send.getGenericParameterTypes()[0]);
        assertEquals(Request.class, parameterType.getRawType(), "send 파라미터는 Request여야 합니다.");
    }
}


