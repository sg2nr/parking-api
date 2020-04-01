package com.parking.parkingapi.controller.mapper;

import com.parking.parkingapi.exception.InvalidInputDataException;
import com.parking.parkingapi.model.order.request.OrderRequest;
import com.parking.parkingapi.model.order.response.CheckOutResponse;
import com.parking.parkingapi.model.order.response.CheckOutResponseBuilder;
import com.parking.parkingapi.model.order.response.OrderResponse;
import com.parking.parkingapi.model.order.response.OrderResponseBuilder;
import com.parking.parkingapi.model.vehicle.Car;
import com.parking.parkingapi.model.order.OrderDto;
import com.parking.parkingapi.model.order.OrderDtoBuilder;
import com.parking.parkingapi.model.vehicle.Vehicle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * JSON - DTO mapper for Order
 */
@Component
public class OrderJsonMapper {

  private static final Logger LOG = LoggerFactory.getLogger(OrderJsonMapper.class);

  public static final String NO_MANDATORY_ELEMENTS_ERROR_MESSAGE = "'carPlate' and 'serviceRequested' are mandatory.";

  public OrderDto mapToDto(OrderRequest request, long parkingId) throws InvalidInputDataException {

    if (Objects.isNull(request.getCarPlate()) || Objects.isNull(request.getServiceRequested())) {
      LOG.error(NO_MANDATORY_ELEMENTS_ERROR_MESSAGE);
      throw new InvalidInputDataException(NO_MANDATORY_ELEMENTS_ERROR_MESSAGE);
    }

    Vehicle vehicle = new Car(request.getCarPlate(), request.getServiceRequested());
    return OrderDtoBuilder.builder()
        .withVehicle(vehicle)
        .withParkingId(parkingId)
        .withTimeStampIn(ZonedDateTime.now())
        .build();
  }

  public OrderResponse mapToResponse(OrderDto orderDto) {
    return OrderResponseBuilder.builder()
        .withCarPlate(orderDto.getVehicle().getPlate())
        .withCheckin(orderDto.getTimeStampIn())
        .withParkingId(orderDto.getParkingId())
        .withSlotNumber(orderDto.getSlotNumber())
        .withOrderId(orderDto.getOrderId())
        .build();
  }

  public CheckOutResponse mapToCheckoutResponse(OrderDto orderDto) {
    OrderResponse orderDetails = mapToResponse(orderDto);

    return CheckOutResponseBuilder.builder()
        .withOrderDetails(orderDetails)
        .withAmountToPay(orderDto.getAmount())
        .withCheckout(orderDto.getTimeStampOut())
        .build();
  }
}
