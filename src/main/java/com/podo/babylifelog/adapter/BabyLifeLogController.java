package com.podo.babylifelog.adapter;

import com.podo.babylifelog.application.request.GetAllBabyLifeLogsRequest;
import com.podo.babylifelog.application.request.GetBabyLifeLogRequest;
import com.podo.babylifelog.application.request.RecordBabyLifeLogRequest;
import com.podo.babylifelog.application.response.BabyLifeLogListResponse;
import com.podo.babylifelog.application.response.BabyLifeLogResponse;
import com.podo.shared.mediator.Mediator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * REST endpoints for baby life log CRUD operations.
 * Uses Mediator pattern to dispatch requests to handlers.
 */
@RestController
@RequestMapping("/api/baby-life-logs")
@RequiredArgsConstructor
public class BabyLifeLogController {

    private final Mediator mediator;

    @PostMapping
    public BabyLifeLogResponse create(@RequestBody RecordBabyLifeLogRequest request) {
        return mediator.send(request);
    }

    @GetMapping
    public BabyLifeLogListResponse getAll() {
        return mediator.send(new GetAllBabyLifeLogsRequest());
    }

    @GetMapping("/{id}")
    public BabyLifeLogResponse getById(@PathVariable Long id) {
        return mediator.send(new GetBabyLifeLogRequest(id));
    }
}
