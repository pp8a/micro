package com.micro.order_service.mapper;

import org.mapstruct.Mapper;

import org.mapstruct.factory.Mappers;

import com.micro.order_service.dto.OrderDTO;
import com.micro.order_service.model.Order;

/**
 * Mapper for the entity Order and its DTO OrderDTO.
 */
@Mapper(componentModel = "spring")
public interface OrderMapper {
	OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);


    /**
     * Converts an Order entity to an OrderDTO.
     *
     * @param order the order entity
     * @return the order DTO
     */
    OrderDTO orderToOrderDTO(Order order);
    /**
     * Converts an OrderDTO to an Order entity.
     *
     * @param orderDTO the order DTO
     * @return the order entity
     */
    Order orderDTOToOrder(OrderDTO orderDTO);
}
