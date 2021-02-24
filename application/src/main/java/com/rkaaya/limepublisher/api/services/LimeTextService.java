package com.rkaaya.limepublisher.api.services;

import com.rkaaya.lime.domain.model.Lime;

public interface LimeTextService {

    /**
     * <p> Process the lime request </p>
     *
     * @param pStart    indicates the start number of paragraphs
     * @param pEnd      indicates the end number of paragraphs
     * @param wCountMin indicates min number of words in each paragraph
     * @param wCountMax indicates max number of words in each paragraph
     * @return the Processed result
     */
    Lime processLime(Integer pStart, Integer pEnd, Integer wCountMin, Integer wCountMax);
}
