package com.micro.restaurant_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderAvroDTO {
	private String id;
    private String orderStatus;
    private double totalAmount;
}
