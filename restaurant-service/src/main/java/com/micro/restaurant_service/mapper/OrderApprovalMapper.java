package com.micro.restaurant_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.micro.restaurant_service.dto.OrderApprovalDTO;
import com.micro.restaurant_service.model.OrderApproval;

@Mapper(componentModel = "spring")
public interface OrderApprovalMapper {
	OrderApprovalMapper INSTANCE = Mappers.getMapper(OrderApprovalMapper.class);
	
	/**
	 * Converts an OrderApproval entity to an OrderApprovalDTO.
	 * @param orderApproval the order approval entity
	 * @return the order approval DTO
	 */
	OrderApprovalDTO orderApprovalToOrderApprovalDTO(OrderApproval orderApproval);
	
	/**
	 * Converts an OrderApprovalDTO to an OrderApproval.
	 * @param orderApprovalDTO the order approval DTO
	 * @return the order approval entity
	 */
	OrderApproval orderApprovalDTOToOrderApproval(OrderApprovalDTO orderApprovalDTO);
}
