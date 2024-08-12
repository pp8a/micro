package com.micro.restaurant_service.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.micro.OrderAvro;
import com.micro.restaurant_service.dto.OrderApprovalDTO;
import com.micro.restaurant_service.dto.OrderAvroDTO;
import com.micro.restaurant_service.exception.EntityNotFoundException;
import com.micro.restaurant_service.mapper.OrderAvroMapper;
import com.micro.restaurant_service.model.OrderApproval;
import com.micro.restaurant_service.model.OrderApprovalStatus;
import com.micro.restaurant_service.repository.OrderApprovalRepository;
import com.micro.restaurant_service.service.OrderApprovalService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrderApprovalKafkaService {
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderApprovalKafkaService.class);
	
	private KafkaTemplate<String, OrderAvro> kafkaTemplate;
	private OrderApprovalRepository approvalRepository;
	private OrderAvroMapper orderAvroMapper;
	private OrderApprovalService approvalService;
	
	@KafkaListener(topics = "restaurant-approval-request-topic", groupId = "restaurant-service-group")
	public void listenRestaurantApprovalRequest(OrderAvro orderAvro) {
		OrderAvroDTO orderAvroDTO = orderAvroMapper.orderAvroToOrderAvroDTO(orderAvro);
		OrderApprovalDTO orderApprovalDTO = orderAvroMapper.orderAvroDTOToOrderApprovalDTO(orderAvroDTO);
		
		if(orderAvro.getOrderStatus().equals(OrderApprovalStatus.PAID.toString())){
			orderApprovalDTO = approvalService.createOrderApproval(orderApprovalDTO);
			orderAvro.setOrderStatus(orderApprovalDTO.getOrderApprovalStatus().toString());
		} else {
			Long orderId = orderApprovalDTO.getOrderId();
			approvalService.cancelOrderApproval(orderId);
			OrderApproval orderApproval = approvalRepository.findByOrderId(orderId)
					.orElseThrow(() -> new EntityNotFoundException(
							"Order approval not found for order ID: " + orderId));
			orderAvro.setOrderStatus(orderApproval.getOrderApprovalStatus().toString());
		}
		
		sendRestaurantApprovalresponse(orderAvro);
	}
	
	public void sendRestaurantApprovalresponse(OrderAvro orderAvro) {
		kafkaTemplate.send("restaurant-approval-response-topic", orderAvro);
	}
}
