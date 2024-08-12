package com.micro.restaurant_service.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.micro.restaurant_service.dto.OrderApprovalDTO;
import com.micro.restaurant_service.exception.EntityNotFoundException;
import com.micro.restaurant_service.service.OrderApprovalService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@AllArgsConstructor
@RequestMapping("/approvals")
public class OrderApprovalController {
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderApprovalController.class);
	
	private OrderApprovalService approvalService;
	
	@PostMapping
	public ResponseEntity<OrderApprovalDTO> createOrderApproval(@RequestBody OrderApprovalDTO orderApprovalDTO) {
		LOGGER.debug("Received request to create order approval: {}", orderApprovalDTO);
		OrderApprovalDTO createdOrderApproval = approvalService.createOrderApproval(orderApprovalDTO);
		LOGGER.info("Order approval created with ID: {}", createdOrderApproval.getId());
		return new ResponseEntity<>(createdOrderApproval, HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<OrderApprovalDTO>> getAllOrdersApproval() {
		LOGGER.debug("Received request to fetch all orders approval");
		List<OrderApprovalDTO> approvals = approvalService.getAllOrderApproval();
		LOGGER.info("Fetched all orders approval");
		return ResponseEntity.ok(approvals);
	}
	
	@GetMapping("/{approvalId}")
	public ResponseEntity<OrderApprovalDTO> getOrderApprovalById(@PathVariable Long approvalId) {
		LOGGER.debug("Received request to fetch the order approval with ID: {}", approvalId);
		OrderApprovalDTO approval = approvalService.getOrderApprovalById(approvalId).orElseThrow(() -> {
			LOGGER.error("Order approval not found with ID: {}", approvalId);
			return new EntityNotFoundException("Order approval not found with id: " + approvalId);
		});
		LOGGER.info("Fetched order approval with ID: {}", approvalId);
		return ResponseEntity.ok(approval);
	}
}
