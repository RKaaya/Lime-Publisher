package com.rkaaya.limepublisher.api.services.messaging;

import com.rkaaya.lime.domain.model.Lime;

public interface ApiEventProducer {
    void publish(Lime message);
}
