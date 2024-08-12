package com.micro.order_service.model;

import java.math.BigDecimal;

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
 * Entity representing an orders.
 */
@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order {
	/**
     * The unique identifier of the order.
     */
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
    private Long id;
	/**
     * The name of the customer.
     */
    @Column(name = "customer_name", nullable = false)
    private String customerName;
    /**
     * The status of the order.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus orderStatus;
    /**
     * The total amount of the order.
     */
    @Column(name = "total_amount")
    private BigDecimal totalAmount;
}