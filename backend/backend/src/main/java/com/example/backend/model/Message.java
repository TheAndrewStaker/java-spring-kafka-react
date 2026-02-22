package com.example.backend.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.Instant;

@Entity
@Table(
        name = "message",
        uniqueConstraints = @UniqueConstraint(columnNames = "idempotencyKeyS")
)
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Column(nullable = false)
    @Getter
    private String content;

    @Column(nullable = false)
    @Getter
    private Instant createdAt = Instant.now();

    @Column(nullable = false, unique = true)
    @Getter
    private String idempotencyKey;

    protected Message() {
    }

    public Message(String content, String idempotencyKey) {
        this.content = content;
        this.idempotencyKey = idempotencyKey;
    }
}
