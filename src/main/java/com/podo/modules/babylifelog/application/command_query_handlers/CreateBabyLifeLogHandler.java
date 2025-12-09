package com.podo.modules.babylifelog.application.command_query_handlers;

import com.podo.modules.babylifelog.application.commands.CreateBabyLifeLogCommand;
import com.podo.modules.babylifelog.application.response.BabyLifeLogResponse;
import com.podo.modules.babylifelog.domain.BabyLifeLogRecord;
import com.podo.modules.babylifelog.domain.BabyLifeLogRepository;
import com.podo.shared.kernel.application.UnitOfWork;
import com.podo.shared.mediator.RequestHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Handler for recording a new baby life log entry.
 */
@Service
@Transactional
public class CreateBabyLifeLogHandler implements RequestHandler<CreateBabyLifeLogCommand, BabyLifeLogResponse> {

    private final BabyLifeLogRepository repository;
    private final UnitOfWork unitOfWork;

    public CreateBabyLifeLogHandler(BabyLifeLogRepository repository,
            UnitOfWork unitOfWork) {
        this.repository = repository;
        this.unitOfWork = unitOfWork;
    }

    @Override
    public BabyLifeLogResponse handle(CreateBabyLifeLogCommand command) {
        BabyLifeLogRecord record = BabyLifeLogRecord.create(
                command.type(),
                command.content(),
                command.occurredAt()
        );
        BabyLifeLogRecord saved = repository.save(record);
        unitOfWork.saveChanges();
        return BabyLifeLogResponse.from(saved);
    }
}
