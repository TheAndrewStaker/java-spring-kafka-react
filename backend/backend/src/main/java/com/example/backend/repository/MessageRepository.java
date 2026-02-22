package com.example.backend.repository;

import com.example.backend.model.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Long> {

    Page<Message> findAllByOrderByCreatedAtDesc(Pageable pageable);
    Optional<Message> findByIdempotencyKey(String idempotencyKey);
}
