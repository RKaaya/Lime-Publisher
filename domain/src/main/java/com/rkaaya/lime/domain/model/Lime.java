package com.rkaaya.lime.domain.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@JsonDeserialize(builder = Lime.LimeBuilder.class)
@Setter
public class Lime {

    @NonNull String freqWord;
    @NonNull Integer avgParagraphSize;
    @NonNull Long avgParagraphProcessingTime;
    @NonNull Long totalProcessingTime;

    @JsonPOJOBuilder(withPrefix = "")
    public static final class LimeBuilder {

    }
}
