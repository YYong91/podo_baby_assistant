package com.podo.messageorchestrator.infrastructure;

import com.podo.messageorchestrator.domain.Message;
import com.podo.messageorchestrator.domain.MessageRouter;
import com.podo.messageorchestrator.domain.MessageType;
import org.springframework.stereotype.Component;

/**
 * Simple implementation of MessageRouter.
 * Routes messages based on keyword matching.
 * TODO: Replace with LLM-based intent classification.
 */
@Component
public class SimpleMessageRouter implements MessageRouter {

    @Override
    public RoutingResult route(Message message) {
        String content = message.getContent().toLowerCase();
        
        // Simple keyword-based routing
        if (containsAny(content, "낮잠", "밤잠", "잤어", "뒤집기", "걸었어", "기저귀", "수유", "이유식")) {
            return new RoutingResult("babylifelog", MessageType.COMMAND, null);
        }
        
        if (containsAny(content, "언제", "몇번", "알려줘", "찾아줘")) {
            return new RoutingResult("babylifelog", MessageType.QUERY, null);
        }
        
        if (containsAny(content, "어떻게", "좋을까", "추천", "조언")) {
            return new RoutingResult("advice", MessageType.ADVICE, null);
        }
        
        return new RoutingResult("unknown", MessageType.UNKNOWN, null);
    }
    
    private boolean containsAny(String text, String... keywords) {
        for (String keyword : keywords) {
            if (text.contains(keyword)) {
                return true;
            }
        }
        return false;
    }
}

