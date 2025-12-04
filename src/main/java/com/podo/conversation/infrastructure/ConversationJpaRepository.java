package com.podo.conversation.infrastructure;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for ConversationJpaEntity.
 */
@Repository
public interface ConversationJpaRepository extends JpaRepository<ConversationJpaEntity, UUID> {

    @Query("SELECT c FROM ConversationJpaEntity c ORDER BY c.lastUpdatedAt DESC")
    List<ConversationJpaEntity> findRecentConversations(org.springframework.data.domain.Pageable pageable);
}

