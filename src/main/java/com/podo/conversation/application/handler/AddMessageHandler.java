package com.podo.conversation.application.handler;

import com.podo.conversation.application.request.AddMessageRequest;
import com.podo.conversation.application.response.ConversationResponse;
import com.podo.conversation.domain.Conversation;
import com.podo.conversation.domain.ConversationRepository;
import com.podo.shared.mediator.RequestHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Handler for AddMessageRequest.
 */
@Component
@RequiredArgsConstructor
public class AddMessageHandler 
    implements RequestHandler<AddMessageRequest, ConversationResponse> {

    private final ConversationRepository conversationRepository;

    @Override
    @Transactional
    public ConversationResponse handle(AddMessageRequest request) {
        Conversation conversation = conversationRepository.findById(request.conversationId())
            .orElseThrow(() -> new IllegalArgumentException(
                "Conversation not found: " + request.conversationId()
            ));
        
        conversation.addMessage(request.role(), request.content());
        Conversation saved = conversationRepository.save(conversation);
        return ConversationResponse.from(saved);
    }
}

