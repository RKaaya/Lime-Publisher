package com.rkaaya.limepublisher.api.services;

import com.rkaaya.limepublisher.api.domain.RandomText;

public interface RandomTextService {

    /**
     * <p> Get a Random text </p>
     *
     * @param number_of_paragraphs             an integer between 1 and 50 indicating how many paragraphs should be generated in the random text
     * @param min_number_of_words_per_sentence an integer between 1 and
     *                                         1000 indicating the minimum number of words that should be generated in
     *                                         each paragraph
     * @param max_number_of_words_per_senteces an integer between 1 and
     *                                         1000 indicating the minimum number of words that should be generated in
     *                                         each paragraph
     * @return the RandomText with the random text
     */
    RandomText getRandomText(Integer number_of_paragraphs, Integer min_number_of_words_per_sentence, Integer max_number_of_words_per_senteces);
}
