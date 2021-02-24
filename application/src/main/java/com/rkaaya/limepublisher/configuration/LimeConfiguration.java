package com.rkaaya.limepublisher.configuration;

import com.rkaaya.limepublisher.api.services.LimeTextProcessor;
import com.rkaaya.limepublisher.api.services.LimeTextService;
import com.rkaaya.limepublisher.api.services.RandomTextService;
import com.rkaaya.limepublisher.api.services.messaging.ApiEventProducer;
import com.rkaaya.limepublisher.infrastructure.application.DefaultLimeTextProcessor;
import com.rkaaya.limepublisher.infrastructure.application.DefaultLimeTextService;
import com.rkaaya.limepublisher.infrastructure.application.DefaultRandomTextService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class LimeConfiguration {

    private String randomTextBasePath = "http://www.randomtext.me/api";

    @Bean
    public LimeTextProcessor limeTextProcessor() {
        return new DefaultLimeTextProcessor(randomTextService());
    }

    @Bean
    public LimeTextService limeTextService(final ApiEventProducer apiEventProducer) {
        return new DefaultLimeTextService(apiEventProducer, limeTextProcessor());
    }

    @Bean
    public RandomTextService randomTextService() {
        return new DefaultRandomTextService(restTemplate(), randomTextBasePath);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
