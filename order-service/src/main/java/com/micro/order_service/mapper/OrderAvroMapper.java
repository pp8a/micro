package com.micro.order_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.micro.OrderAvro;
import com.micro.order_service.dto.OrderDTO;
import com.micro.order_service.model.OrderStatus;

@Mapper(componentModel = "spring")
public interface OrderAvroMapper {
	OrderAvroMapper INSTANCE = Mappers.getMapper(OrderAvroMapper.class);
	
	@Mapping(target = "id", source = "id", qualifiedByName = "toStringId")
    @Mapping(target = "orderStatus", source = "orderStatus", qualifiedByName = "toStringOrderStatus")
	@Mapping(target = "customerName", source = "customerName", qualifiedByName = "toString")
	OrderAvro entityToAvro(OrderDTO order);
	
	@Mapping(target = "id", source = "id", qualifiedByName = "toLongId")
    @Mapping(target = "orderStatus", source = "orderStatus", qualifiedByName = "toOrderStatus")
	@Mapping(target = "customerName", source = "customerName", qualifiedByName = "toString")
	OrderDTO avroToEntity(OrderAvro avroOrder);
	
	@Named("toStringId")
    default String toStringId(Long id) {
        return id != null ? id.toString() : null;
    }

	@Named("toLongId")
    default Long toLongId(CharSequence id) {
        return id != null ? Long.valueOf(id.toString()) : null;
    }

    @Named("toStringOrderStatus")
    default String toStringOrderStatus(OrderStatus orderStatus) {
        return orderStatus != null ? orderStatus.name() : null;
    }

    @Named("toOrderStatus")
    default OrderStatus toOrderStatus(CharSequence orderStatus) {
        return orderStatus != null ? OrderStatus.valueOf(orderStatus.toString()) : null;
    }
    
    @Named("toString")
    default String toString(CharSequence charSequence) {
        return charSequence != null ? charSequence.toString() : null;
    }

    @Named("toCharSequence")
    default CharSequence toCharSequence(String string) {
        return string;
    }
}
