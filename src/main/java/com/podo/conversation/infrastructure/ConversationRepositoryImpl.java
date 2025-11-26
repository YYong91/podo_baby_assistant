package com.podo.conversation.infrastructure;

import com.podo.conversation.domain.Conversation;
import com.podo.conversation.domain.ConversationMessage;
import com.podo.conversation.domain.ConversationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of ConversationRepository port.
 * Bridges domain repository interface with Spring Data JPA.
 */
@Repository
@RequiredArgsConstructor
public class ConversationRepositoryImpl implements ConversationRepository {

    private final ConversationJpaRepository jpaRepository;

    @Override
    public Conversation save(Conversation conversation) {
        ConversationJpaEntity entity = toEntity(conversation);
        ConversationJpaEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<Conversation> findById(String id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Conversation> findRecent(int limit) {
        return jpaRepository.findRecentConversations(PageRequest.of(0, limit))
            .stream()
            .map(this::toDomain)
            .toList();
    }

    @Override
    public void delete(Conversation conversation) {
        jpaRepository.deleteById(conversation.getId());
    }

    private ConversationJpaEntity toEntity(Conversation domain) {
        ConversationJpaEntity entity = new ConversationJpaEntity(
            domain.getId(),
            domain.getCreatedAt(),
            domain.getLastUpdatedAt()
        );

        List<ConversationMessageJpaEntity> messageEntities = domain.getMessages().stream()
            .map(msg -> new ConversationMessageJpaEntity(
                msg.getId(),
                entity,
                msg.getRole(),
                msg.getContent(),
                msg.getCreatedAt()
            ))
            .toList();

        entity.setMessages(new java.util.ArrayList<>(messageEntities));
        return entity;
    }

    private Conversation toDomain(ConversationJpaEntity entity) {
        List<ConversationMessage> messages = entity.getMessages().stream()
            .map(msg -> ConversationMessage.reconstitute(
                msg.getId(),
                msg.getRole(),
                msg.getContent(),
                msg.getCreatedAt()
            ))
            .toList();

        return Conversation.reconstitute(
            entity.getId(),
            messages,
            entity.getCreatedAt(),
            entity.getLastUpdatedAt()
        );
    }
}

