package com.podo.shared.infrastructure;

import com.podo.shared.mediator.Request;
import com.podo.shared.mediator.RequestHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

class SpringMediatorTest {

    private AnnotationConfigApplicationContext context;
    private SpringMediator mediator;

    @BeforeEach
    void setUp() {
        context = new AnnotationConfigApplicationContext();
        context.registerBean(TestRequestHandler.class);
        context.refresh();
        mediator = new SpringMediator(context);
    }

    @AfterEach
    void tearDown() {
        context.close();
    }

    @Test
    void send_shouldRouteRequestToMatchingHandler() {
        String response = mediator.send(new TestRequest("첫 번째 요청"));

        assertEquals("handled: 첫 번째 요청", response);
    }

    @Test
    void send_shouldThrow_whenHandlerMissing() {
        AnnotationConfigApplicationContext emptyContext = new AnnotationConfigApplicationContext();
        emptyContext.refresh();
        SpringMediator emptyMediator = new SpringMediator(emptyContext);

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> emptyMediator.send(new TestRequest("없는 핸들러")));

        assertTrue(exception.getMessage().contains(TestRequest.class.getName()));
        emptyContext.close();
    }

    private record TestRequest(String payload) implements Request<String> {
    }

    private static class TestRequestHandler implements RequestHandler<TestRequest, String> {

        @Override
        public String handle(TestRequest request) {
            return "handled: " + request.payload();
        }
    }
}

