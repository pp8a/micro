package com.micro.payment_service.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.micro.payment_service.model.PaymentStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentDTO {
	private Long id;
    private Long orderId;
    private PaymentStatus paymentStatus;
    private BigDecimal amount;
    private LocalDateTime createdAt;
}
