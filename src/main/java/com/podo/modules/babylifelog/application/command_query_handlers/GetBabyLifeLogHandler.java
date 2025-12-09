package com.podo.modules.babylifelog.application.command_query_handlers;

import com.podo.modules.babylifelog.application.queries.GetBabyLifeLogQuery;
import com.podo.modules.babylifelog.application.response.BabyLifeLogResponse;
import com.podo.modules.babylifelog.domain.BabyLifeLogRepository;
import com.podo.shared.mediator.RequestHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Handler for getting a baby life log entry by id.
 */
@Service
@Transactional(readOnly = true)
public class GetBabyLifeLogHandler implements RequestHandler<GetBabyLifeLogQuery, BabyLifeLogResponse> {

    private final BabyLifeLogRepository repository;

    public GetBabyLifeLogHandler(BabyLifeLogRepository repository) {
        this.repository = repository;
    }

    @Override
    public BabyLifeLogResponse handle(GetBabyLifeLogQuery query) {
        return repository.findById(query.id())
                .map(BabyLifeLogResponse::from)
                .orElseThrow(() -> new IllegalArgumentException("BabyLifeLog not found: " + query.id()));
    }
}
