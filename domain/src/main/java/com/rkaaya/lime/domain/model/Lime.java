package com.rkaaya.lime.domain.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Data;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@JsonDeserialize(builder = Lime.LimeBuilder.class)
public class Lime {

    @NonNull String freqWord;
    @NonNull Integer avgParagraphSize;
    @NonNull BigDecimal avgParagraphProcessingTime;
    @NonNull BigDecimal totalProcessingTime;

    @JsonPOJOBuilder(withPrefix = "")
    public static final class LimeBuilder {

    }
}
