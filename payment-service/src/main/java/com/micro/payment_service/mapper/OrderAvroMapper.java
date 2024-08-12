package com.micro.payment_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.micro.OrderAvro;
import com.micro.payment_service.dto.OrderAvroDTO;
import com.micro.payment_service.dto.PaymentDTO;
import com.micro.payment_service.model.PaymentStatus;

@Mapper(componentModel = "spring")
public interface OrderAvroMapper {
	OrderAvroMapper INSTANCE = Mappers.getMapper(OrderAvroMapper.class);
	
	@Mapping(target = "id", source = "id",  qualifiedByName = "toLongId")
    @Mapping(target = "orderStatus", source = "orderStatus", qualifiedByName = "toString")
    @Mapping(target = "totalAmount", source = "totalAmount")
	OrderAvroDTO orderAvroToOrderAvroDTO(OrderAvro orderAvro);	
	
	@Mapping(target = "orderId", source = "id", qualifiedByName = "toLongId")
	@Mapping(target = "amount", source = "totalAmount")
	@Mapping(target = "paymentStatus", source = "orderStatus", qualifiedByName = "toPaymentStatus")
	@Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
	PaymentDTO orderAvroDTOToPaymentDTO(OrderAvroDTO orderAvroDTO);
	
	@Named("toLongId")
    default Long toLongId(CharSequence id) {
        return id != null ? Long.valueOf(id.toString()) : null;
    }	
	
	@Named("toString")
    default String toString(CharSequence charSequence) {
        return charSequence != null ? charSequence.toString() : null;
    }
	
	@Named("toPaymentStatus")
	default PaymentStatus toPaymentStatus(String orderStatus) {
        if (orderStatus == null) {
            return null;
        }
        switch (orderStatus) {
            case "PENDING":
                return PaymentStatus.PENDING;    
            case "CANCELLING":
                return PaymentStatus.CANCELLING;  
            case "CANCELLED":
            	return PaymentStatus.CANCELLED;
            default:
                throw new IllegalArgumentException("Unknown order status: " + orderStatus);
        }
    }
	
}
