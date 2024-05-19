package com.javachinna.repo;

import com.javachinna.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepo extends JpaRepository<Payment, Long> {
    Payment findBySessionId(String sessionId);
}
