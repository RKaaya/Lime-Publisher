package com.rkaaya.limepublisher.infrastructure;

import com.rkaaya.limepublisher.api.domain.LimePartialResult;

import java.util.HashMap;
import java.util.Map;

public class BaseTest {

    protected static final String MOST_FREQUENT_WORD = "most";
    protected static final String LESS_FREQUENT_WORD = "less";

    public LimePartialResult getLimePartialResult(Integer paragraph, Long processing, String... args) {
        Map<String, Long> words = new HashMap<>();
        for(String item : args) {
            words.computeIfPresent(item, (k,v) -> v + 1);
            words.putIfAbsent(item, 1L);
        }
        return LimePartialResult.builder()
                .paragraphCount(paragraph)
                .processingTime(processing)
                .words(words)
                .build();
    }
}
