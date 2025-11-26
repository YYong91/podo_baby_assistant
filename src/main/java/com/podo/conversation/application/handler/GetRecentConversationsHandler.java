package com.podo.conversation.application.handler;

import com.podo.conversation.application.request.GetRecentConversationsRequest;
import com.podo.conversation.application.response.ConversationListResponse;
import com.podo.conversation.domain.Conversation;
import com.podo.conversation.domain.ConversationRepository;
import com.podo.shared.mediator.RequestHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Handler for GetRecentConversationsRequest.
 */
@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetRecentConversationsHandler 
    implements RequestHandler<GetRecentConversationsRequest, ConversationListResponse> {

    private final ConversationRepository conversationRepository;

    @Override
    public ConversationListResponse handle(GetRecentConversationsRequest request) {
        List<Conversation> conversations = conversationRepository.findRecent(request.limit());
        return ConversationListResponse.from(conversations);
    }
}

