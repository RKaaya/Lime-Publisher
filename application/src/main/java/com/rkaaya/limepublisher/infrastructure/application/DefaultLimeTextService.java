package com.rkaaya.limepublisher.infrastructure.application;

import com.rkaaya.lime.domain.model.Lime;
import com.rkaaya.limepublisher.api.domain.LimePartialResult;
import com.rkaaya.limepublisher.api.services.*;
import com.rkaaya.limepublisher.api.services.messaging.ApiEventProducer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@AllArgsConstructor
@Slf4j
public class DefaultLimeTextService implements LimeTextService {

    private ApiEventProducer apiEventProducer;
    private LimeTextProcessor limeTextProcessor;

    @Override
    public Lime processLime(Integer pStart, Integer pEnd, Integer wCountMin, Integer wCountMax) {
        Lime result = null;
        final long start = System.nanoTime();
        try {
            final Integer caseCount = (pEnd - pStart) + 1;
            CompletableFuture<LimePartialResult>[] partialResults = new CompletableFuture[caseCount];

            for (int i = 0; i < caseCount; i++) {
                partialResults[i] = limeTextProcessor.processRequest(i + pStart, 1, 25);
            }

            CompletableFuture.allOf(partialResults).join();

            final List<LimePartialResult> limePartialResults = new ArrayList<>();
            for (CompletableFuture<LimePartialResult> partialResult : partialResults) {
                LimePartialResult limePartialResult = partialResult.get();
                limePartialResults.add(limePartialResult);
            }

            String mostFrequentWord = limeTextProcessor.calculateMostFrequentWord(limePartialResults);
            Long avgProcessTime = limeTextProcessor.calculateAvgParagraphProcessingTime(limePartialResults);
            Integer avgSize = limeTextProcessor.calculateAvgParagraphSize(limePartialResults);

            final long finish = System.nanoTime();

            result = Lime.builder()
                    .freqWord(mostFrequentWord)
                    .avgParagraphProcessingTime(avgProcessTime)
                    .avgParagraphSize(avgSize)
                    .totalProcessingTime(finish - start)
                    .build();

            log.info("Result: {}", result);
            apiEventProducer.publish(result);
        } catch (Exception ex) {
            log.error("SOMETHING HAPPENED: {}", ex.getMessage());
        }
        return result;
    }
}