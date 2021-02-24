package com.rkaaya.limepublisher.api.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class LimePartialResult {
    Map<String, Long> words;
    Integer paragraphCount;
    Long processingTime;
}
