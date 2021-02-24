package com.rkaaya.limepublisher.infrastructure.messaging;

import com.rkaaya.lime.domain.model.Lime;
import com.rkaaya.limepublisher.api.services.messaging.ApiChannel;
import com.rkaaya.limepublisher.api.services.messaging.ApiEventProducer;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.MimeTypeUtils;

@Slf4j
@AllArgsConstructor
public class KafkaEventProducer implements ApiEventProducer {

    private final ApiChannel target;
    private final long requestTimeout;

    @Override
    public void publish(@NonNull final Lime msg) {
        final Message<Lime> message = MessageBuilder
                .withPayload(msg)
                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                .setHeader(KafkaHeaders.MESSAGE_KEY, msg.getFreqWord())
                .build();

        log.debug("MESSAGE --- Start producing message.");
        if (!target.output().send(message, requestTimeout)) {
            log.warn("Failed to send message: {}", msg);
            throw new MessageDeliveryException("Failed to send message");
        }
        log.debug("MESSAGE --- Finished producing message.");
    }
}
