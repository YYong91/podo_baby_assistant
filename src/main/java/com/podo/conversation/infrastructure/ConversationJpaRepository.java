package com.podo.conversation.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for ConversationJpaEntity.
 */
@Repository
public interface ConversationJpaRepository extends JpaRepository<ConversationJpaEntity, String> {

    @Query("SELECT c FROM ConversationJpaEntity c ORDER BY c.lastUpdatedAt DESC")
    List<ConversationJpaEntity> findRecentConversations(org.springframework.data.domain.Pageable pageable);
}

