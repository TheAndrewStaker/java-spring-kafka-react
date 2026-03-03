package com.example.backend.service;

import com.example.backend.model.Message;
import com.example.backend.observability.CorrelationIdFilter;
import com.example.backend.repository.MessageRepository;
import jakarta.transaction.Transactional;
import org.slf4j.MDC;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    public static final String TOPIC = "backend-events";

    private final MessageRepository messageRepository;
    private final KafkaTemplate<String, String> kafka;

    public MessageService(MessageRepository messageRepository, KafkaTemplate<String, String> kafka) {
        this.messageRepository = messageRepository;
        this.kafka = kafka;
    }

    @Transactional
    public Message create(String content, String idempotencyKey) {

        return messageRepository.findByIdempotencyKey(idempotencyKey)
                .orElseGet(() -> {
                    Message savedMessage = messageRepository.save(
                            new Message(content, idempotencyKey)
                    );

                    publishEvent(savedMessage);

                    return savedMessage;
                });
    }

    private void publishEvent(Message message) {

        String correlationId = MDC.get(CorrelationIdFilter.MDC_KEY);

        var msgBuilder = MessageBuilder
                .withPayload(message.getContent())
                .setHeader(KafkaHeaders.TOPIC, TOPIC)
                .setHeader(KafkaHeaders.KEY, message.getId().toString());

        if (correlationId != null && !correlationId.isBlank()) {
            msgBuilder.setHeader(CorrelationIdFilter.HEADER_NAME, correlationId);
        }

        kafka.send(msgBuilder.build());
    }
}