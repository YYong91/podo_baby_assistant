package com.podo.conversation.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository port for Conversation aggregate.
 * This is an interface (port) - implementation is in infrastructure layer.
 */
public interface ConversationRepository {

    Conversation save(Conversation conversation);

    Optional<Conversation> findById(UUID id);

    List<Conversation> findRecent(int limit);

    void delete(Conversation conversation);
}

