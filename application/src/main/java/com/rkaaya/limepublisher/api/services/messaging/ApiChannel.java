package com.rkaaya.limepublisher.api.services.messaging;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface ApiChannel {
    String OUTPUT = "limte-text-out";

    @Output(OUTPUT)
    MessageChannel output();
}
