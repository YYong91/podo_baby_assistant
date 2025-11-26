package com.podo.conversation.application.handler;

import com.podo.conversation.application.request.GetConversationRequest;
import com.podo.conversation.application.response.ConversationResponse;
import com.podo.conversation.domain.Conversation;
import com.podo.conversation.domain.ConversationRepository;
import com.podo.shared.mediator.RequestHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Handler for GetConversationRequest.
 */
@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetConversationHandler 
    implements RequestHandler<GetConversationRequest, ConversationResponse> {

    private final ConversationRepository conversationRepository;

    @Override
    public ConversationResponse handle(GetConversationRequest request) {
        Conversation conversation = conversationRepository.findById(request.conversationId())
            .orElseThrow(() -> new IllegalArgumentException(
                "Conversation not found: " + request.conversationId()
            ));
        
        return ConversationResponse.from(conversation);
    }
}

