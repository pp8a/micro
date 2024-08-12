package com.micro.restaurant_service.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * Entity representing an order approval.
 */
@Entity
@Getter
@Setter
@Table(name = "order_approvals")
public class OrderApproval {
	/**
     * The unique identifier of the order approval.
     */
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
    private Long id;
	
	@Column(name = "order_id")
	private Long orderId;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "restaurant_status")
	private OrderApprovalStatus orderApprovalStatus;
	
	@Column(name = "created_at")
	private LocalDateTime createdAt;
}
