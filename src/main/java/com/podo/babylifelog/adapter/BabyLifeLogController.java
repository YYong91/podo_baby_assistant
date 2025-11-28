package com.podo.babylifelog.adapter;

import com.podo.babylifelog.application.commands.CreateBabyLifeLogCommand;
import com.podo.babylifelog.application.queries.GetAllBabyLifeLogsQuery;
import com.podo.babylifelog.application.queries.GetBabyLifeLogQuery;
import com.podo.babylifelog.application.response.BabyLifeLogListResponse;
import com.podo.babylifelog.application.response.BabyLifeLogResponse;
import com.podo.shared.mediator.Mediator;
import lombok.RequiredArgsConstructor;

import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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
    public BabyLifeLogResponse create(@RequestBody CreateBabyLifeLogCommand command) {
        return mediator.send(command);
    }

    @GetMapping
    public BabyLifeLogListResponse getAll() {
        return mediator.send(new GetAllBabyLifeLogsQuery());
    }

    @GetMapping("/{id}")
    public BabyLifeLogResponse getById(@PathVariable @NonNull UUID id) {
        return mediator.send(new GetBabyLifeLogQuery(id));
    }
}
