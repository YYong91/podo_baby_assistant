package com.podo.babylifelog.application.handler;

import com.podo.babylifelog.application.query.GetBabyLifeLogQuery;
import com.podo.babylifelog.application.response.BabyLifeLogResponse;
import com.podo.babylifelog.domain.BabyLifeLogRepository;
import com.podo.shared.mediator.RequestHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Handler for getting a baby life log entry by id.
 */
@Component
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
