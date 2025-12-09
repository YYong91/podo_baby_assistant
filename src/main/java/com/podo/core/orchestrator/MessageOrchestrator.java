package com.podo.core.orchestrator;

import com.podo.shared.mediator.Mediator;
import com.podo.shared.mediator.Request;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Application service that wires Brain ←→ Mediator dispatch.
 */
@Component
public class MessageOrchestrator {

    private static final String REPLY_SEPARATOR = "\n\n";

    private final BrainClient brainClient;
    private final Mediator mediator;

    public MessageOrchestrator(BrainClient brainClient, Mediator mediator) {
        this.brainClient = Objects.requireNonNull(brainClient, "brainClient must not be null");
        this.mediator = Objects.requireNonNull(mediator, "mediator must not be null");
    }

    public OrchestratorResult orchestrate(UserUtterance utterance) {
        Objects.requireNonNull(utterance, "utterance must not be null");

        BrainResult brainResult = Objects.requireNonNull(
                brainClient.analyze(utterance),
                "brainClient returned null result"
        );

        IntentType intent = brainResult.resolvedIntent();
        Object moduleResponse = dispatchIfNeeded(intent, utterance, brainResult);

        String replyText = mergeReplies(brainResult.replyDraft(), moduleResponse);

        return new OrchestratorResult(
                utterance.conversationId(),
                replyText,
                intent,
                brainResult.isAboutBaby(),
                brainResult.shouldStoreConversation()
        );
    }

    private Object dispatchIfNeeded(IntentType intent, UserUtterance utterance, BrainResult brainResult) {
        return switch (intent) {
            case RECORD_BABY_LOG -> dispatch(new RecordBabyLogRequest(
                    requireBabyId(utterance),
                    utterance.text(),
                    utterance.timestamp(),
                    brainResult.slots()
            ));
            case RECOMMEND_BOOK_AND_ACTIVITY -> dispatch(new RecommendBookAndActivityRequest(
                    requireBabyId(utterance),
                    utterance.text(),
                    utterance.timestamp(),
                    brainResult.slots()
            ));
            case HEALTH_CONCERN -> dispatch(new HealthConcernAdviceRequest(
                    requireBabyId(utterance),
                    utterance.text(),
                    utterance.timestamp(),
                    brainResult.slots()
            ));
            case DAYCARE_QUESTION -> dispatch(new DaycareQuestionAdviceRequest(
                    requireBabyId(utterance),
                    utterance.text(),
                    utterance.timestamp(),
                    brainResult.slots()
            ));
            case SMALL_TALK, UNKNOWN -> null;
        };
    }

    private String requireBabyId(UserUtterance utterance) {
        if (!utterance.hasBabyContext()) {
            throw new IllegalArgumentException("babyId is required for this intent");
        }
        return utterance.babyId();
    }

    private <TResponse> TResponse dispatch(Request<TResponse> request) {
        // TODO: Replace placeholder request types with module-specific ones once modules are ready.
        return mediator.send(request);
    }

    private String mergeReplies(String brainDraft, Object moduleResponse) {
        String base = normalize(brainDraft);
        String module = normalize(extractModuleReply(moduleResponse));

        if (module.isEmpty()) {
            return base;
        }
        if (base.isEmpty()) {
            return module;
        }
        return base + REPLY_SEPARATOR + module;
    }

    private String normalize(String text) {
        return text == null ? "" : text.trim();
    }

    private String extractModuleReply(Object moduleResponse) {
        if (moduleResponse == null) {
            return "";
        }
        if (moduleResponse instanceof CharSequence sequence) {
            return sequence.toString();
        }
        if (moduleResponse instanceof Optional<?> optional) {
            return optional.map(this::extractModuleReply).orElse("");
        }
        return moduleResponse.toString();
    }

    private interface MediatorIntentRequest<T> extends Request<T> {
        String babyId();

        String description();

        Instant timestamp();

        Map<String, Object> slots();
    }

    private static final class RecordBabyLogRequest extends BaseMediatorIntentRequest<Object> {
        private RecordBabyLogRequest(String babyId, String description, Instant timestamp, Map<String, Object> slots) {
            super(babyId, description, timestamp, slots);
        }
    }

    private static final class RecommendBookAndActivityRequest extends BaseMediatorIntentRequest<Object> {
        private RecommendBookAndActivityRequest(String babyId, String description, Instant timestamp, Map<String, Object> slots) {
            super(babyId, description, timestamp, slots);
        }
    }

    private static final class HealthConcernAdviceRequest extends BaseMediatorIntentRequest<Object> {
        private HealthConcernAdviceRequest(String babyId, String description, Instant timestamp, Map<String, Object> slots) {
            super(babyId, description, timestamp, slots);
        }
    }

    private static final class DaycareQuestionAdviceRequest extends BaseMediatorIntentRequest<Object> {
        private DaycareQuestionAdviceRequest(String babyId, String description, Instant timestamp, Map<String, Object> slots) {
            super(babyId, description, timestamp, slots);
        }
    }

    private abstract static class BaseMediatorIntentRequest<T> implements MediatorIntentRequest<T> {

        private final String babyId;
        private final String description;
        private final Instant timestamp;
        private final Map<String, Object> slots;

        protected BaseMediatorIntentRequest(String babyId,
                String description,
                Instant timestamp,
                Map<String, Object> slots) {
            this.babyId = Objects.requireNonNull(babyId, "babyId must not be null");
            this.description = Objects.requireNonNull(description, "description must not be null");
            this.timestamp = Objects.requireNonNull(timestamp, "timestamp must not be null");
            this.slots = slots == null ? Map.of() : Map.copyOf(slots);
        }

        @Override
        public String babyId() {
            return babyId;
        }

        @Override
        public String description() {
            return description;
        }

        @Override
        public Instant timestamp() {
            return timestamp;
        }

        @Override
        public Map<String, Object> slots() {
            return slots;
        }
    }
}
