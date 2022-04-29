package com.parking.parkingapi.controller.mapper;

import com.parking.parkingapi.exception.InvalidInputDataException;
import com.parking.parkingapi.model.common.ParkingServiceType;
import com.parking.parkingapi.model.order.Order;
import com.parking.parkingapi.model.order.request.OrderRequest;
import com.parking.parkingapi.model.order.response.OrderResponse;
import com.parking.parkingapi.model.vehicle.Car;
import com.parking.parkingapi.model.vehicle.Vehicle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.ZonedDateTime;

import static java.time.ZonedDateTime.now;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderJsonMapperTest {

  private static final int ORDER_ID = 589;

  private static final String CAR_PLATE = "AAAA";

  private static final long PARKING_ID = 1L;

  private static final int SLOT_NUMBER = 5;

  @Autowired
  OrderJsonMapper orderJsonMapper;

  @Test
  void testMapCheckIn() throws InvalidInputDataException {
    OrderRequest orderRequest = new OrderRequest();
    orderRequest.setCarPlate(CAR_PLATE);
    orderRequest.setServiceRequested(ParkingServiceType.FIFTY_KW_POWER_SUPPLY);

    Order order = orderJsonMapper.mapToOrder(orderRequest, PARKING_ID);

    assertNotNull(order);
    assertEquals(CAR_PLATE, order.getVehicle().getPlate());
    assertEquals(ParkingServiceType.FIFTY_KW_POWER_SUPPLY, order.getVehicle().getRequestedService());
    assertNotNull(order.getTimeStampIn());
  }

  @Test
  void testMapCheckInInvalidData() {
    OrderRequest orderRequest = new OrderRequest();
    orderRequest.setCarPlate(CAR_PLATE);

    Exception exception = assertThrows(InvalidInputDataException.class,
        () -> orderJsonMapper.mapToOrder(orderRequest, PARKING_ID));

    String expectedMessage = "'carPlate' and 'serviceRequested' are mandatory.";
    String actualMessage = exception.getMessage();

    assertEquals(expectedMessage, actualMessage);
  }

  @Test
  void testMapToResponse() {
    Order order = new Order();
    ZonedDateTime dateTime = now();
    order.setTimeStampIn(dateTime);
    order.setParkingId(PARKING_ID);
    order.setOrderId(ORDER_ID);
    order.setSlotNumber(SLOT_NUMBER);

    Vehicle vehicle = new Car(CAR_PLATE, ParkingServiceType.FIFTY_KW_POWER_SUPPLY);
    order.setVehicle(vehicle);

    OrderResponse orderResponse = orderJsonMapper.mapToResponse(order);

    assertNotNull(orderResponse);
    assertEquals(PARKING_ID, orderResponse.getParkingId());
    assertEquals(CAR_PLATE, orderResponse.getCarPlate());
    assertEquals(dateTime, orderResponse.getCheckin());
    assertEquals(ORDER_ID, orderResponse.getOrderId());
    assertEquals(SLOT_NUMBER, orderResponse.getSlotNumber());
  }
}