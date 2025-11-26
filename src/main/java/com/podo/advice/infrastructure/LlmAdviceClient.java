package com.podo.advice.infrastructure;

import com.podo.advice.domain.Advice;
import com.podo.advice.domain.AdviceGenerator;
import org.springframework.stereotype.Component;

/**
 * LLM API client implementation for advice generation.
 * Implements the AdviceGenerator port from domain layer.
 */
@Component
public class LlmAdviceClient implements AdviceGenerator {

    @Override
    public Advice generate(String query) {
        // TODO: Implement actual LLM API call (e.g., OpenAI, Claude, etc.)
        String generatedContent = "This is a placeholder advice for: " + query;
        return Advice.create(query, generatedContent);
    }
}

