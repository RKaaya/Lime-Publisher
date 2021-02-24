package com.rkaaya.limepublisher.infrastructure.application;

import com.rkaaya.limepublisher.api.domain.RandomText;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplateHandler;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
class DefaultRandomTextServiceTest {

    private static RestTemplate restTemplate;

    private static String randomTextBasePath;

    private static DefaultRandomTextService defaultRandomTextService;

    @BeforeAll
    static void beforeAll() {
        randomTextBasePath = "localhost";
        restTemplate = Mockito.mock(RestTemplate.class);
        defaultRandomTextService = new DefaultRandomTextService(restTemplate, randomTextBasePath);
    }

    @Test
    void getRandomText() throws URISyntaxException {
        final Integer number_of_paragraphs = 1;
        final Integer min_number_of_words_per_sentence = 1;
        final Integer max_number_of_words_per_senteces = 1;
        RandomText randomText = Mockito.mock(RandomText.class);
        ResponseEntity responseEntity = Mockito.mock(ResponseEntity.class);

        Mockito.when(responseEntity.getBody()).thenReturn(randomText);
        UriTemplateHandler uriTemplateHandler = Mockito.mock(UriTemplateHandler.class);
        URI uri = new URI("/get");

        Mockito.when(restTemplate.getUriTemplateHandler()).thenReturn(uriTemplateHandler);
        Mockito.when(uriTemplateHandler.expand(Mockito.any(String.class), Mockito.any(Map.class))).thenReturn(uri);

        Mockito.when(restTemplate.getForEntity((randomTextBasePath + uri.toString()), RandomText.class)).thenReturn(responseEntity);

        RandomText result = defaultRandomTextService.getRandomText(number_of_paragraphs, min_number_of_words_per_sentence, max_number_of_words_per_senteces);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(randomText, result);
    }

    @Test
    void getRandomTextThrowException() {
        final Integer number_of_paragraphs = null;
        final Integer min_number_of_words_per_sentence = 1;
        final Integer max_number_of_words_per_senteces = 1;

        Assertions.assertThrows(HttpClientErrorException.class, () -> defaultRandomTextService.getRandomText(number_of_paragraphs, min_number_of_words_per_sentence, max_number_of_words_per_senteces));
    }
}