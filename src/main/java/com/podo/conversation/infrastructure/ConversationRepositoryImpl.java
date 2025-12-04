package com.podo.conversation.infrastructure;

import com.podo.conversation.domain.Conversation;
import com.podo.conversation.domain.ConversationMessage;
import com.podo.conversation.domain.ConversationRepository;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

/**
 * Implementation of ConversationRepository port.
 * Bridges domain repository interface with Spring Data JPA.
 */
@Repository
@RequiredArgsConstructor
@SuppressWarnings("NullAway")
public class ConversationRepositoryImpl implements ConversationRepository {

    private final ConversationJpaRepository jpaRepository;

    @Override
    public Conversation save(Conversation conversation) {
        ConversationJpaEntity entity = toEntity(conversation);
        ConversationJpaEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<Conversation> findById(UUID id) {
        UUID safeId = Objects.requireNonNull(id, "conversation id must not be null");
        return jpaRepository.findById(safeId).map(this::toDomain);
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
        UUID id = Objects.requireNonNull(conversation.getId(), "conversation id must not be null");
        jpaRepository.deleteById(id);
    }

    private ConversationJpaEntity toEntity(Conversation domain) {
        UUID conversationId = Objects.requireNonNull(domain.getId(), "conversation id must not be null");
        ConversationJpaEntity entity = new ConversationJpaEntity(
            conversationId,
            domain.getCreatedAt(),
            domain.getLastUpdatedAt()
        );

        List<ConversationMessageJpaEntity> messageEntities = domain.getMessages().stream()
            .map(msg -> new ConversationMessageJpaEntity(
                Objects.requireNonNull(msg.getId(), "message id must not be null"),
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

