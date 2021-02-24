package com.rkaaya.limepublisher.api.services;

import com.rkaaya.limepublisher.api.domain.LimePartialResult;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface LimeTextProcessor {
    CompletableFuture<LimePartialResult> processRequest(Integer number_of_paragraphs, Integer min_number_of_words_per_sentence, Integer max_number_of_words_per_senteces);
    String calculateMostFrequentWord(List<LimePartialResult> limePartialResults);
    Long calculateAvgParagraphProcessingTime(List<LimePartialResult> limePartialResults);
    Integer calculateAvgParagraphSize(List<LimePartialResult> limePartialResults);
}
