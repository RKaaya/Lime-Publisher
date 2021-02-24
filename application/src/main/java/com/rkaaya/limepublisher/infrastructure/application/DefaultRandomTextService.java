package com.rkaaya.limepublisher.infrastructure.application;

import com.rkaaya.limepublisher.api.domain.RandomText;
import com.rkaaya.limepublisher.api.services.RandomTextService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@AllArgsConstructor
public class DefaultRandomTextService implements RandomTextService {
    private RestTemplate restTemplate;
    private String randomTextBasePath;

    @Override
    public RandomText getRandomText(final Integer number_of_paragraphs,final Integer min_number_of_words_per_sentence,final Integer max_number_of_words_per_senteces) {
        return getRandomTextWithHttpInfo(number_of_paragraphs, min_number_of_words_per_sentence, max_number_of_words_per_senteces).getBody();
    }

    private ResponseEntity<RandomText> getRandomTextWithHttpInfo(final Integer number_of_paragraphs, final Integer min_number_of_words_per_sentence, final Integer max_number_of_words_per_senteces) throws RestClientException {
        // verify the required parameters are set
        if (number_of_paragraphs == null || min_number_of_words_per_sentence == null || max_number_of_words_per_senteces == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'number_of_paragraphs' when calling getRandomTextWithHttpInfo");
        }

        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<>();
        uriVariables.put("number_of_paragraphs", number_of_paragraphs);
        uriVariables.put("min_number_of_words_per_sentence", min_number_of_words_per_sentence);
        uriVariables.put("max_number_of_words_per_senteces", max_number_of_words_per_senteces);
        String path = expandPath("/giberish/p-{number_of_paragraphs}/{min_number_of_words_per_sentence}-{max_number_of_words_per_senteces}", uriVariables);

        // call endpoint
        log.info("Calling basepath: {}, path: {}", randomTextBasePath, path);
        ResponseEntity<RandomText> randomText = restTemplate.getForEntity(randomTextBasePath + path, RandomText.class);
        //TODO handle exceptions
        return randomText;
    }

    private String expandPath(String pathTemplate, Map<String, Object> variables) {
        return restTemplate.getUriTemplateHandler().expand(pathTemplate, variables).toString();
    }
}
