package com.parking.parkingapi.controller.mapper;

import com.parking.parkingapi.exception.InvalidInputDataException;
import com.parking.parkingapi.model.car.Car;
import com.parking.parkingapi.model.car.OrderDto;
import com.parking.parkingapi.model.car.OrderDtoBuilder;
import com.parking.parkingapi.model.car.Vehicle;
import com.parking.parkingapi.model.car.request.OrderRequest;
import com.parking.parkingapi.model.car.response.OrderResponse;
import com.parking.parkingapi.model.car.response.OrderResponseBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.time.ZonedDateTime;
import java.util.Objects;

@Component
public class OrderJsonMapper {

  private static final Logger LOG = LoggerFactory.getLogger(OrderJsonMapper.class);

  public static final String NO_MANDATORY_ELEMENTS_ERROR_MESSAGE = "'carPlate' and 'serviceRequested' are mandatory.";

  public OrderDto mapToDto(OrderRequest request, @Valid long parkingId) throws InvalidInputDataException {

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
        .build();
  }
}
