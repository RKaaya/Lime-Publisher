package com.rkaaya.limepublisher.configuration;

import com.rkaaya.limepublisher.api.services.messaging.ApiChannel;
import com.rkaaya.limepublisher.api.services.messaging.ApiEventProducer;
import com.rkaaya.limepublisher.infrastructure.messaging.KafkaEventProducer;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;

@EnableBinding(ApiChannel.class)
public class EventProducerConfiguration {
    private static final long REQUEST_TIMEOUT = 10000;

    @Bean
    public ApiEventProducer apiEventProducer(final ApiChannel target) {
        return new KafkaEventProducer(target, REQUEST_TIMEOUT);
    }

}
