package com.podo.advice.domain;

/**
 * Port interface for advice generation (LLM).
 * Implementation will be in infrastructure layer.
 */
public interface AdviceGenerator {

    /**
     * Generates advice based on the given query.
     * @param query User query
     * @return Generated advice
     */
    Advice generate(String query);
}

