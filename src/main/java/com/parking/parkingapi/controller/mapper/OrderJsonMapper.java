package com.parking.parkingapi.controller.mapper;

import com.parking.parkingapi.exception.InvalidInputDataException;
import com.parking.parkingapi.model.order.request.OrderRequest;
import com.parking.parkingapi.model.order.response.CheckOutResponse;
import com.parking.parkingapi.model.order.response.CheckOutResponseBuilder;
import com.parking.parkingapi.model.order.response.OrderResponse;
import com.parking.parkingapi.model.order.response.OrderResponseBuilder;
import com.parking.parkingapi.model.vehicle.Car;
import com.parking.parkingapi.model.order.Order;
import com.parking.parkingapi.model.order.OrderBuilder;
import com.parking.parkingapi.model.vehicle.Vehicle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * JSON - Order internal datamodel mapper
 */
@Component
public class OrderJsonMapper {

  private static final Logger LOG = LoggerFactory.getLogger(OrderJsonMapper.class);

  public static final String NO_MANDATORY_ELEMENTS_ERROR_MESSAGE = "'carPlate' and 'serviceRequested' are mandatory.";

  public Order mapToOrder(OrderRequest request, long parkingId) throws InvalidInputDataException {

    if (Objects.isNull(request.getCarPlate()) || Objects.isNull(request.getServiceRequested())) {
      LOG.error(NO_MANDATORY_ELEMENTS_ERROR_MESSAGE);
      throw new InvalidInputDataException(NO_MANDATORY_ELEMENTS_ERROR_MESSAGE);
    }

    Vehicle vehicle = new Car(request.getCarPlate(), request.getServiceRequested());
    return OrderBuilder.builder()
        .withVehicle(vehicle)
        .withParkingId(parkingId)
        .withTimeStampIn(ZonedDateTime.now())
        .build();
  }

  public OrderResponse mapToResponse(Order order) {
    return OrderResponseBuilder.builder()
        .withCarPlate(order.getVehicle().getPlate())
        .withCheckin(order.getTimeStampIn())
        .withParkingId(order.getParkingId())
        .withSlotNumber(order.getSlotNumber())
        .withOrderId(order.getOrderId())
        .build();
  }

  public CheckOutResponse mapToCheckoutResponse(Order order) {
    OrderResponse orderDetails = mapToResponse(order);

    return CheckOutResponseBuilder.builder()
        .withOrderDetails(orderDetails)
        .withAmountToPay(order.getAmount())
        .withCheckout(order.getTimeStampOut())
        .build();
  }
}
