package com.podo.babylifelog.application.handler;

import com.podo.babylifelog.application.request.GetBabyLifeLogRequest;
import com.podo.babylifelog.application.response.BabyLifeLogResponse;
import com.podo.babylifelog.domain.BabyLifeLog;
import com.podo.babylifelog.domain.BabyLifeLogRepository;
import com.podo.shared.mediator.RequestHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Handler for GetBabyLifeLogRequest.
 */
@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetBabyLifeLogHandler 
    implements RequestHandler<GetBabyLifeLogRequest, BabyLifeLogResponse> {

    private final BabyLifeLogRepository babyLifeLogRepository;

    @Override
    public BabyLifeLogResponse handle(GetBabyLifeLogRequest request) {
        BabyLifeLog log = babyLifeLogRepository.findById(request.id())
            .orElseThrow(() -> new IllegalArgumentException(
                "BabyLifeLog not found: " + request.id()
            ));
        
        return BabyLifeLogResponse.from(log);
    }
}

