package com.podo.modules.babylifelog.application.command_query_handlers;

import com.podo.modules.babylifelog.application.queries.GetAllBabyLifeLogsQuery;
import com.podo.modules.babylifelog.application.response.BabyLifeLogListResponse;
import com.podo.modules.babylifelog.domain.BabyLifeLogRepository;
import com.podo.shared.mediator.RequestHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Handler for getting all baby life log entries.
 */
@Component
@Transactional(readOnly = true)
public class GetAllBabyLifeLogsHandler implements RequestHandler<GetAllBabyLifeLogsQuery, BabyLifeLogListResponse> {

    private final BabyLifeLogRepository repository;

    public GetAllBabyLifeLogsHandler(BabyLifeLogRepository repository) {
        this.repository = repository;
    }

    @Override
    public BabyLifeLogListResponse handle(GetAllBabyLifeLogsQuery query) {
        return BabyLifeLogListResponse.from(repository.findAll());
    }
        }
