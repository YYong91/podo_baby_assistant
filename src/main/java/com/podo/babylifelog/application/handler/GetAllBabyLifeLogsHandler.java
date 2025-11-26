package com.podo.babylifelog.application.handler;

import com.podo.babylifelog.application.request.GetAllBabyLifeLogsRequest;
import com.podo.babylifelog.application.response.BabyLifeLogListResponse;
import com.podo.babylifelog.domain.BabyLifeLog;
import com.podo.babylifelog.domain.BabyLifeLogRepository;
import com.podo.shared.mediator.RequestHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Handler for GetAllBabyLifeLogsRequest.
 */
@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetAllBabyLifeLogsHandler 
    implements RequestHandler<GetAllBabyLifeLogsRequest, BabyLifeLogListResponse> {

    private final BabyLifeLogRepository babyLifeLogRepository;

    @Override
    public BabyLifeLogListResponse handle(GetAllBabyLifeLogsRequest request) {
        List<BabyLifeLog> logs = babyLifeLogRepository.findAll();
        return BabyLifeLogListResponse.from(logs);
    }
}

