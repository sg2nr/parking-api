package com.parking.parkingapi.service;

import com.parking.parkingapi.TestUtils;
import com.parking.parkingapi.exception.EntityCreationViolation;
import com.parking.parkingapi.exception.EntityNotFoundException;
import com.parking.parkingapi.exception.NoSlotAvailableException;
import com.parking.parkingapi.exception.VehicleAlreadyParkedException;
import com.parking.parkingapi.model.common.ParkingServiceType;
import com.parking.parkingapi.model.order.Order;
import com.parking.parkingapi.model.vehicle.Car;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static java.time.ZonedDateTime.now;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CheckInOrchestratorTest {

  @Autowired
  CheckInOrchestrator checkInOrchestrator;

  @Test
  void testCheckIn() throws NoSlotAvailableException, EntityNotFoundException, EntityCreationViolation, VehicleAlreadyParkedException {
    Order checkInOrder = createOrderRequestWithStandardCar();

    Order confirmationOrder = checkInOrchestrator.run(checkInOrder);
    assertNotNull(confirmationOrder);
    assertNotNull(confirmationOrder.getTimeStampIn());
    assertNull(confirmationOrder.getTimeStampOut());
    assertEquals(checkInOrder.getVehicle(), confirmationOrder.getVehicle());
    assertEquals(checkInOrder.getParkingId(), confirmationOrder.getParkingId());
    assertNotEquals(0, confirmationOrder.getSlotNumber());
  }

  @Test
  void testSlotNotAvailable() {
    Order checkInOrder = createOrderRequestWithElectricalCar();
    assertThrows(NoSlotAvailableException.class, () -> checkInOrchestrator.run(checkInOrder));
  }

  public static Order createOrderRequestWithStandardCar() {
    Order order = new Order();

    order.setParkingId(TestUtils.PARKING_ID_1);
    order.setTimeStampIn(now());
    order.setVehicle(new Car(TestUtils.PLATE_1, ParkingServiceType.STANDARD_PARKING));

    return order;
  }

  public static Order createOrderRequestWithElectricalCar() {
    Order order = new Order();

    order.setParkingId(TestUtils.PARKING_ID_1);
    order.setTimeStampIn(now());
    order.setVehicle(new Car(TestUtils.PLATE_2, ParkingServiceType.FIFTY_KW_POWER_SUPPLY));

    return order;
  }

}