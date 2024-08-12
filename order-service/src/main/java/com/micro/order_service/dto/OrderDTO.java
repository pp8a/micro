package com.micro.order_service.dto;

import java.math.BigDecimal;

import com.micro.order_service.model.OrderStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDTO {
	/**
     * The unique identifier of the order.
     */
	private Long id;
    private String customerName;
    private OrderStatus orderStatus;
    private BigDecimal totalAmount;

}
