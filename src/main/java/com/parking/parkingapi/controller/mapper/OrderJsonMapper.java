package com.parking.parkingapi.controller.mapper;

import com.parking.parkingapi.model.car.Car;
import com.parking.parkingapi.model.car.OrderDto;
import com.parking.parkingapi.model.car.OrderDtoBuilder;
import com.parking.parkingapi.model.car.Vehicle;
import com.parking.parkingapi.model.car.request.OrderRequest;
import com.parking.parkingapi.model.car.response.OrderResponse;
import com.parking.parkingapi.model.car.response.OrderResponseBuilder;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.time.ZonedDateTime;

@Component
public class OrderJsonMapper {

  public OrderDto mapToDto(OrderRequest request, @Valid long parkingId) {

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
