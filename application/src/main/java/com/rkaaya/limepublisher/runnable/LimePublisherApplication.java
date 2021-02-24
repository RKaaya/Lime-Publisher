package com.rkaaya.limepublisher.runnable;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@SpringBootApplication(scanBasePackages = {"com.rkaaya.limepublisher.configuration", "com.rkaaya.limepublisher.infrastructure.interfaces"})
@EnableAsync
public class LimePublisherApplication {

    public static void main(String[] args) {
        SpringApplication.run(LimePublisherApplication.class, args);
    }

    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("LimeProcessor-");
        executor.initialize();
        return executor;
    }
}
