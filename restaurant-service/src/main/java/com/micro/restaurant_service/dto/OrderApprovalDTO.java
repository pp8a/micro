package com.micro.restaurant_service.dto;

import java.time.LocalDateTime;

import com.micro.restaurant_service.model.OrderApprovalStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderApprovalDTO {
	private Long id;
    private Long orderId;
    private OrderApprovalStatus orderApprovalStatus;
    private LocalDateTime createdAt;
}
