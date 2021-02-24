package com.rkaaya.limepublisher.infrastructure.application;

import com.rkaaya.lime.domain.model.Lime;
import com.rkaaya.limepublisher.api.domain.LimePartialResult;
import com.rkaaya.limepublisher.api.services.LimeTextProcessor;
import com.rkaaya.limepublisher.api.services.messaging.ApiEventProducer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultLimeTextServiceTest {

    @Mock
    private ApiEventProducer apiEventProducer;
    @Mock
    private LimeTextProcessor limeTextProcessor;

    @InjectMocks
    private DefaultLimeTextService limeTextService;

    @Test
    @Disabled
    void processLime() {
        final Integer pStart = 2;
        final Integer pEnd = 3;
        final Integer wCountMin = 4;
        final Integer wCountMax = 5;

        final String mostFW = "word";
        final Long avgT = 2L;
        final Integer avgP = 2;

        LimePartialResult limePartialResult = Mockito.mock(LimePartialResult.class);

        CompletableFuture<LimePartialResult> limePartialResultCompletableFuture = Mockito.mock(CompletableFuture.class);

        Mockito.doReturn(limePartialResultCompletableFuture).when(limeTextProcessor)
                .processRequest(Mockito.any(), Mockito.any(), Mockito.any());

        Mockito.when(limeTextProcessor.processRequest(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(limePartialResultCompletableFuture);

        Mockito.when(limeTextProcessor.calculateMostFrequentWord(Mockito.anyList())).thenReturn(mostFW);
        Mockito.when(limeTextProcessor.calculateAvgParagraphProcessingTime(Mockito.anyList())).thenReturn(avgT);
        Mockito.when(limeTextProcessor.calculateAvgParagraphSize(Mockito.anyList())).thenReturn(avgP);

        Lime lime = limeTextService.processLime(pStart, pEnd, wCountMin, wCountMax);

        verify(limeTextProcessor.processRequest(2,4,5), times(1));
        verify(limeTextProcessor.processRequest(3,4,5), times(1));
        verify(apiEventProducer, times(1)).publish(lime);

        Assertions.assertNotNull(lime);
        Assertions.assertEquals(mostFW, lime.getFreqWord());
        Assertions.assertEquals(avgT, lime.getTotalProcessingTime());
        Assertions.assertEquals(avgP, lime.getAvgParagraphSize());

    }
}