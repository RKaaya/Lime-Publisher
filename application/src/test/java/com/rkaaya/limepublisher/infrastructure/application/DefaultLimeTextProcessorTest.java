package com.rkaaya.limepublisher.infrastructure.application;

import com.rkaaya.limepublisher.api.domain.LimePartialResult;
import com.rkaaya.limepublisher.api.domain.RandomText;
import com.rkaaya.limepublisher.api.services.RandomTextService;
import com.rkaaya.limepublisher.infrastructure.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DefaultLimeTextProcessorTest extends BaseTest {

    @Mock
    private RandomTextService randomTextService;

    @InjectMocks
    private DefaultLimeTextProcessor defaultLimeTextProcessor;

    @Test
    void processRequest() throws ExecutionException, InterruptedException {
        Integer current_paragraph = 1;
        Integer min_number_of_words_per_sentence = 1;
        Integer max_number_of_words_per_senteces = 1;
        RandomText randomText = Mockito.mock(RandomText.class);
        Mockito.when(randomText.getText_out()).thenReturn(MOST_FREQUENT_WORD + " " + LESS_FREQUENT_WORD);
        Mockito.when(randomText.getAmount()).thenReturn(2);

        Mockito.when(randomTextService.getRandomText(current_paragraph, min_number_of_words_per_sentence, max_number_of_words_per_senteces)).thenReturn(randomText);

        CompletableFuture<LimePartialResult> result = defaultLimeTextProcessor.processRequest(current_paragraph, min_number_of_words_per_sentence, max_number_of_words_per_senteces);

        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.get());

        LimePartialResult limePartialResult = result.get();

        Assertions.assertEquals(2, limePartialResult.getParagraphCount());
        Assertions.assertEquals(2, limePartialResult.getWords().size());
        Assertions.assertEquals(true, limePartialResult.getWords().containsKey(MOST_FREQUENT_WORD));
        Assertions.assertEquals(false, limePartialResult.getWords().containsKey("Not"));
    }

    @Test
    void processRequestRandomString() throws ExecutionException, InterruptedException {
        Integer current_paragraph = 1;
        Integer min_number_of_words_per_sentence = 1;
        Integer max_number_of_words_per_senteces = 1;
        RandomText randomText = Mockito.mock(RandomText.class);
        Mockito.when(randomText.getText_out()).thenReturn("<p> asd asd  zxc asd   @$@ asd . 1401- $4201 asdz.... *()      </p> asd . asd   zxc ... mm mm");
        Mockito.when(randomText.getAmount()).thenReturn(2);

        Mockito.when(randomTextService.getRandomText(current_paragraph, min_number_of_words_per_sentence, max_number_of_words_per_senteces)).thenReturn(randomText);

        CompletableFuture<LimePartialResult> result = defaultLimeTextProcessor.processRequest(current_paragraph, min_number_of_words_per_sentence, max_number_of_words_per_senteces);

        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.get());

        LimePartialResult limePartialResult = result.get();

        Assertions.assertEquals(4, limePartialResult.getWords().size());
    }

    @Test
    void processRequestEmptyString() throws ExecutionException, InterruptedException {
        Integer current_paragraph = 1;
        Integer min_number_of_words_per_sentence = 1;
        Integer max_number_of_words_per_senteces = 1;
        RandomText randomText = Mockito.mock(RandomText.class);
        Mockito.when(randomText.getText_out()).thenReturn("   ....    ");
        Mockito.when(randomText.getAmount()).thenReturn(2);

        Mockito.when(randomTextService.getRandomText(current_paragraph, min_number_of_words_per_sentence, max_number_of_words_per_senteces)).thenReturn(randomText);

        CompletableFuture<LimePartialResult> result = defaultLimeTextProcessor.processRequest(current_paragraph, min_number_of_words_per_sentence, max_number_of_words_per_senteces);

        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.get());

        LimePartialResult limePartialResult = result.get();

        Assertions.assertEquals(0, limePartialResult.getWords().size());
    }

    @Test
    void calculateMostFrequentWord() {
        List<LimePartialResult> list = new ArrayList<>();
        list.add(getLimePartialResult(1,1L, MOST_FREQUENT_WORD, LESS_FREQUENT_WORD));
        list.add(getLimePartialResult(1,1L, MOST_FREQUENT_WORD, MOST_FREQUENT_WORD, LESS_FREQUENT_WORD));

        String result = defaultLimeTextProcessor.calculateMostFrequentWord(list);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(MOST_FREQUENT_WORD, result);
    }

    @Test
    void calculateAvgParagraphProcessingTime() {
        List<LimePartialResult> list = new ArrayList<>();
        list.add(getLimePartialResult(1,1L, MOST_FREQUENT_WORD, LESS_FREQUENT_WORD));
        list.add(getLimePartialResult(1,3L, MOST_FREQUENT_WORD, MOST_FREQUENT_WORD, LESS_FREQUENT_WORD));

        Long result = defaultLimeTextProcessor.calculateAvgParagraphProcessingTime(list);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(2L, result);
    }

    @Test
    void calculateAvgParagraphSize() {
        List<LimePartialResult> list = new ArrayList<>();
        list.add(getLimePartialResult(1,1L, MOST_FREQUENT_WORD, LESS_FREQUENT_WORD));
        list.add(getLimePartialResult(9,1L, MOST_FREQUENT_WORD, MOST_FREQUENT_WORD, LESS_FREQUENT_WORD));

        Integer result = defaultLimeTextProcessor.calculateAvgParagraphSize(list);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(5, result);
    }
}