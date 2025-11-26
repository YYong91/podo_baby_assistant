package com.podo.babylifelog.application.handler;

import com.podo.babylifelog.application.request.RecordBabyLifeLogRequest;
import com.podo.babylifelog.application.response.BabyLifeLogResponse;
import com.podo.babylifelog.domain.BabyLifeLog;
import com.podo.babylifelog.domain.BabyLifeLogRepository;
import com.podo.babylifelog.domain.LogType;
import com.podo.shared.mediator.RequestHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Handler for RecordBabyLifeLogRequest.
 */
@Component
@RequiredArgsConstructor
public class RecordBabyLifeLogHandler 
    implements RequestHandler<RecordBabyLifeLogRequest, BabyLifeLogResponse> {

    private final BabyLifeLogRepository babyLifeLogRepository;

    @Override
    @Transactional
    public BabyLifeLogResponse handle(RecordBabyLifeLogRequest request) {
        LogType logType = LogType.valueOf(request.logType());
        
        BabyLifeLog log = BabyLifeLog.create(
            logType,
            request.description(),
            request.occurredAt()
        );
        
        BabyLifeLog saved = babyLifeLogRepository.save(log);
        return BabyLifeLogResponse.from(saved);
    }
}

