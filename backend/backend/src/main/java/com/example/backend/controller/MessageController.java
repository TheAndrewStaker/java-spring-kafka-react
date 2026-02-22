package com.example.backend.controller;

import com.example.backend.model.Message;
import com.example.backend.repository.MessageRepository;
import com.example.backend.service.MessageService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("api/messages")
@CrossOrigin(origins = "http://localhost:5173")
public class MessageController {

    private final MessageRepository messageRepository;
    private final MessageService messageService;

    public MessageController(MessageRepository messageRepository, MessageService messageService) {
        this.messageRepository = messageRepository;
        this.messageService = messageService;
    }

    @GetMapping("/latest")
    public String latest() {
        return messageRepository.findAll().stream()
                .max(Comparator.comparing(Message::getCreatedAt))
                .map(Message::getContent)
                .orElse("No messages yet.");
    }

    @GetMapping
    public List<Message> recent(@RequestParam(defaultValue = "20") int limit) {
        int safeLimit = Math.min(Math.max(limit, 1), 100);
        return messageRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(0, safeLimit)).getContent();
    }

    @PostMapping
    public Message create(
            @RequestHeader("Idempotency-Key") String idempotencyKey,
            @Valid @RequestBody CreateMessageRequest request
    ) {
        return messageService.create(request.content(), idempotencyKey);
    }

    public record CreateMessageRequest(@NotBlank String content) {
    }
}
