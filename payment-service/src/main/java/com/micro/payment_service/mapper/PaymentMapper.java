package com.micro.payment_service.mapper; 

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.micro.payment_service.dto.PaymentDTO;
import com.micro.payment_service.model.Payment;

/**
 * Mapper for the entity Payment and its DTO PaymentDTO.
 */
@Mapper(componentModel = "spring")
public interface PaymentMapper {
	PaymentMapper INSTANCE = Mappers.getMapper(PaymentMapper.class);
	
	/**
	 * Converts a Payment entity to a PaymentDTO.
	 * @param payment the payment entity
	 * @return the payment DTO
	 */
	PaymentDTO paymentToPaymentDTO(Payment payment);
	
	/**
	 * Converts a PaymentDTO to a Payment entity.
	 * @param paymentDTO the payment DTO
	 * @return the payment entity
	 */
	Payment paymentDTOToPayment(PaymentDTO paymentDTO);
}
