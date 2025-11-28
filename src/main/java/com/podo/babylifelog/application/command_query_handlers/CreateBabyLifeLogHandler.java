package com.podo.babylifelog.application.command_query_handlers;

import com.podo.babylifelog.application.commands.CreateBabyLifeLogCommand;
import com.podo.babylifelog.application.response.BabyLifeLogResponse;
import com.podo.babylifelog.domain.BabyLifeLogRecord;
import com.podo.babylifelog.domain.BabyLifeLogRepository;
import com.podo.shared.mediator.RequestHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Handler for recording a new baby life log entry.
 */
@Component
@Transactional
public class CreateBabyLifeLogHandler implements RequestHandler<CreateBabyLifeLogCommand, BabyLifeLogResponse> {

    private final BabyLifeLogRepository repository;

    public CreateBabyLifeLogHandler(BabyLifeLogRepository repository) {
        this.repository = repository;
    }

    @Override
    public BabyLifeLogResponse handle(CreateBabyLifeLogCommand command) {
        BabyLifeLogRecord record = BabyLifeLogRecord.create(
                command.type(),
                command.content(),
                command.occurredAt()
        );
        BabyLifeLogRecord saved = repository.save(record);
        return BabyLifeLogResponse.from(saved);
    }
}
