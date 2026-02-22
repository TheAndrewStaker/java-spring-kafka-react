package com.example.backend.service;

import com.example.backend.model.Message;
import com.example.backend.repository.MessageRepository;
import jakarta.transaction.Transactional;
import org.springframework.kafka.core.KafkaTemplate;
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
                    Message message = messageRepository.save(
                            new Message(content, idempotencyKey)
                    );
                    kafka.send(TOPIC, message.getId().toString(), message.getContent());
                    return message;
                });
    }
}
