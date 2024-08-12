package com.micro.restaurant_service.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.micro.restaurant_service.dto.OrderApprovalDTO;
import com.micro.restaurant_service.exception.EntityDeletionException;
import com.micro.restaurant_service.exception.EntityNotFoundException;
import com.micro.restaurant_service.mapper.OrderApprovalMapper;
import com.micro.restaurant_service.model.OrderApproval;
import com.micro.restaurant_service.model.OrderApprovalStatus;
import com.micro.restaurant_service.repository.OrderApprovalRepository;
import com.micro.restaurant_service.service.OrderApprovalService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrderApprovalServiceImpl implements OrderApprovalService {
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderApprovalServiceImpl.class);
	
	private OrderApprovalRepository approvalRepository;
	private OrderApprovalMapper approvalMapper;

	@Override
	public OrderApprovalDTO createOrderApproval(OrderApprovalDTO orderApprovalDTO) {
		LOGGER.debug("Creating a new order approval: {}", orderApprovalDTO);
		OrderApproval orderApproval = approvalMapper.orderApprovalDTOToOrderApproval(orderApprovalDTO);
		orderApproval.setOrderApprovalStatus(OrderApprovalStatus.APPROVED);
		orderApproval = approvalRepository.save(orderApproval);
		LOGGER.info("Order approval created with ID: {}", orderApproval.getId());
		
		return approvalMapper.orderApprovalToOrderApprovalDTO(orderApproval);
	}

	@Override
	public void cancelOrderApproval(Long orderId) {
		approvalRepository.findByOrderId(orderId).ifPresent(approval -> {
			approval.setOrderApprovalStatus(OrderApprovalStatus.CANCELLING);
			approvalRepository.save(approval);
			LOGGER.info("Order approval for order ID: {} has been cancelled", orderId);
		});
	}

	@Override
	public Optional<OrderApprovalDTO> getOrderApprovalById(Long approvalId) {
		LOGGER.debug("Fetching order approval with ID: {}", approvalId);
		return approvalRepository.findById(approvalId)
				.map(approvalMapper::orderApprovalToOrderApprovalDTO);
	}

	@Override
	public List<OrderApprovalDTO> getAllOrderApproval() {
		LOGGER.debug("Fetching all orders approval");
		return approvalRepository.findAll().stream()
				.map(approvalMapper::orderApprovalToOrderApprovalDTO).toList();
	}

	@Override
	public OrderApprovalDTO updateOrderApprovalDTO(Long approvalId, OrderApprovalDTO orderApprovalDTO) {
		LOGGER.debug("Updating order approval with ID: {}", approvalId);
		return approvalRepository.findById(approvalId).map(approval ->{
			approval.setOrderApprovalStatus(orderApprovalDTO.getOrderApprovalStatus());
			OrderApproval updatedApproval = approvalRepository.save(approval);
			LOGGER.info("Order approval updated with ID: {}", updatedApproval.getId());
			return approvalMapper.orderApprovalToOrderApprovalDTO(updatedApproval);
		}).orElseThrow(() -> new EntityNotFoundException("Order approval not found with id: " + orderApprovalDTO));
	}

	@Override
	public void deleteOrderApproval(Long approvalId) {
		LOGGER.debug("Deleting order approval with ID: {}", approvalId);
		if(approvalRepository.existsById(approvalId)) {
			approvalRepository.deleteById(approvalId);
			LOGGER.info("Order approval deleted with ID: {}", approvalId);
		} else {
			LOGGER.error("Payment not found with ID: {}", approvalId);
			throw new EntityDeletionException("Order approval not found with id: " + approvalId);
		}
	}

}
