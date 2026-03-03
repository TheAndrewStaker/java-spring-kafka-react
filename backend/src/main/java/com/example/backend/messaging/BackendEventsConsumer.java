package com.example.backend.messaging;

import com.example.backend.observability.CorrelationIdFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
public class BackendEventsConsumer {
    private static final Logger log = LoggerFactory.getLogger(BackendEventsConsumer.class);

    @KafkaListener(topics = "backend-events", groupId = "backend-group")
    public void onMessage(
            String message,
            @Header(name = CorrelationIdFilter.HEADER_NAME, required = false) String correlationId
    ) {
        if (correlationId != null && !correlationId.isBlank()) {
            MDC.put(CorrelationIdFilter.MDC_KEY, correlationId);
        }

        try {
            log.info("Kafka received: {}", message);
        } finally {
            MDC.remove(CorrelationIdFilter.MDC_KEY);
        }
    }
}
