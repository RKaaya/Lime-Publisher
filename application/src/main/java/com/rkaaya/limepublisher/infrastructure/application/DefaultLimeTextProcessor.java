package com.rkaaya.limepublisher.infrastructure.application;

import com.rkaaya.limepublisher.api.domain.LimePartialResult;
import com.rkaaya.limepublisher.api.domain.RandomText;
import com.rkaaya.limepublisher.api.services.LimeTextProcessor;
import com.rkaaya.limepublisher.api.services.RandomTextService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

@Slf4j
@AllArgsConstructor
public class DefaultLimeTextProcessor implements LimeTextProcessor {

    private RandomTextService randomTextService;

    @Override
    @Async
    public CompletableFuture<LimePartialResult> processRequest(Integer current_paragraph, Integer min_number_of_words_per_sentence, Integer max_number_of_words_per_senteces) {
        final RandomText randomText = randomTextService.getRandomText(current_paragraph, min_number_of_words_per_sentence, max_number_of_words_per_senteces);
        final LimePartialResult limePartialResult = processParagraph(randomText);
        return CompletableFuture.completedFuture(limePartialResult);
    }

    @Override
    public String calculateMostFrequentWord(List<LimePartialResult> limePartialResults) {
        Map<String, Long> mergedParagraphs = mergeParagraphs(limePartialResults).get();
        return mergedParagraphs.entrySet().stream().max(Map.Entry.comparingByValue()).get().getKey();
    }

    @Override
    public Long calculateAvgParagraphProcessingTime(List<LimePartialResult> limePartialResults) {
        OptionalDouble processTimeAvg = limePartialResults.stream().map(LimePartialResult::getProcessingTime).mapToLong(Long::longValue).average();
        return (long)processTimeAvg.getAsDouble();
    }

    @Override
    public Integer calculateAvgParagraphSize(List<LimePartialResult> limePartialResults) {
        OptionalDouble paragraphAvg = limePartialResults.stream().map(LimePartialResult::getParagraphCount).mapToInt(Integer::intValue).average();
        return new Double(paragraphAvg.getAsDouble()).intValue();
    }

    private Optional<Map<String, Long>> mergeParagraphs(List<LimePartialResult> limePartialResults) {
        return limePartialResults.stream().map(LimePartialResult::getWords).reduce((xMap, yMap) -> {
            return Stream.concat(xMap.entrySet().stream(), yMap.entrySet().stream())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                            (countinXMap, countInYMap) -> countinXMap + countInYMap));
        });
    }

    private LimePartialResult processParagraph(final RandomText randomText) {
        final long start = System.nanoTime();
        Map<String, Long> words = Stream.of(randomText.getText_out().split(" "))
                .parallel()
                .map(word -> word.replaceAll("(<p>|</p>|[^a-zA-Z])", "").toLowerCase().trim())
                .filter(word -> word.length() > 0)
                .map(word -> new AbstractMap.SimpleEntry<>(word, 1))
                .collect(groupingBy(AbstractMap.SimpleEntry::getKey, counting()));

        final long finish = System.nanoTime();
        return LimePartialResult.builder()
                .words(words)
                .paragraphCount(randomText.getAmount())
                .processingTime(finish - start)
                .build();
    }
}
