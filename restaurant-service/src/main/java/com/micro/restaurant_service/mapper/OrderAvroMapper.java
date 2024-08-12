package com.micro.restaurant_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.micro.OrderAvro;
import com.micro.restaurant_service.dto.OrderApprovalDTO;
import com.micro.restaurant_service.dto.OrderAvroDTO;
import com.micro.restaurant_service.model.OrderApprovalStatus;

@Mapper(componentModel = "spring")
public interface OrderAvroMapper {
	OrderAvroMapper INSTANCE = Mappers.getMapper(OrderAvroMapper.class);
	
	@Mapping(target = "id", source = "id",  qualifiedByName = "toLongId")
    @Mapping(target = "orderStatus", source = "orderStatus", qualifiedByName = "toString")
	OrderAvroDTO orderAvroToOrderAvroDTO(OrderAvro orderAvro);
	
	@Mapping(target = "orderId", source = "id", qualifiedByName = "toLongId")
	@Mapping(target = "orderApprovalStatus", source = "orderStatus", qualifiedByName = "toApprovalStatus")
	@Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
	OrderApprovalDTO orderAvroDTOToOrderApprovalDTO(OrderAvroDTO orderAvroDTO);
	
	@Named("toLongId")
    default Long toLongId(CharSequence id) {
        return id != null ? Long.valueOf(id.toString()) : null;
    }	
	
	@Named("toString")
    default String toString(CharSequence charSequence) {
        return charSequence != null ? charSequence.toString() : null;
    }
	
	@Named("toApprovalStatus")
	default OrderApprovalStatus toApprovalStatus(String approvalStatus) {
        if (approvalStatus == null) {
            return null;
        }
        switch (approvalStatus) {
            case "PAID":
                return OrderApprovalStatus.PAID;    
            case "CANCELLING":
                return OrderApprovalStatus.CANCELLING;  
            case "APPROVED":
            	return OrderApprovalStatus.APPROVED;
            default:
                throw new IllegalArgumentException("Unknown order approval status: " + approvalStatus);
        }
    }
}
