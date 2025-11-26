package com.podo.conversation.application.handler;

import com.podo.conversation.application.request.StartConversationRequest;
import com.podo.conversation.application.response.ConversationResponse;
import com.podo.conversation.domain.Conversation;
import com.podo.conversation.domain.ConversationRepository;
import com.podo.shared.mediator.RequestHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Handler for StartConversationRequest.
 */
@Component
@RequiredArgsConstructor
public class StartConversationHandler 
    implements RequestHandler<StartConversationRequest, ConversationResponse> {

    private final ConversationRepository conversationRepository;

    @Override
    @Transactional
    public ConversationResponse handle(StartConversationRequest request) {
        Conversation conversation = Conversation.create();
        Conversation saved = conversationRepository.save(conversation);
        return ConversationResponse.from(saved);
    }
}

