package com.rkaaya.limepublisher.api.domain;

import lombok.Getter;

@Getter
public class RandomText {

    /**
     * the type of the generated dummy text data (in this case always
     * "gibberish")
     */
    String type;

    /**
     * the number of paragraphs generated
     */
    Integer amount;

    /**
     * the minimum number of words generated in each paragraph
     */
    Integer number;

    /**
     * the maximum number of words generated in
     * each paragraph
     */
    Integer number_max;

    /**
     * the format of the generated dummy text data (in this case always
     * "p" indicating the data will be generated in html paragraphs)
     */
    String format;

    /**
     * the time of the request
     */
    String time;

    /**
     * the generated dummy text
     */
    String text_out;
}
