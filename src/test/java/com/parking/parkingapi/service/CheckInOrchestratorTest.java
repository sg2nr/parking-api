package com.parking.parkingapi.service;

import com.parking.parkingapi.TestUtils;
import com.parking.parkingapi.dao.ParkingLogsDao;
import com.parking.parkingapi.dao.ParkingSlotDao;
import com.parking.parkingapi.exception.EntityNotFoundException;
import com.parking.parkingapi.exception.NoSlotAvailableException;
import com.parking.parkingapi.model.entities.ParkingLogEntity;
import com.parking.parkingapi.model.entities.ParkingSlotEntity;
import com.parking.parkingapi.model.entities.VehicleEntity;
import com.parking.parkingapi.model.order.Order;
import com.parking.parkingapi.model.parking.Parking;
import com.parking.parkingapi.service.mapper.ParkingServiceTypeConverter;
import com.parking.parkingapi.service.mapper.VehicleMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CheckInOrchestratorTest {

  CheckInOrchestrator checkInOrchestrator;

  ParkingManagerService parkingManagerService;

  VehicleManagerService vehicleManagerService;

  ParkingSlotDao parkingSlotDao;

  ParkingLogsDao parkingLogsDao;

  @BeforeAll
  public void setUp() {
    parkingSlotDao = Mockito.mock(ParkingSlotDao.class);
    parkingLogsDao = Mockito.mock(ParkingLogsDao.class);
    vehicleManagerService = Mockito.mock(VehicleManagerService.class);

    ParkingServiceTypeConverter parkingServiceTypeConverter = new ParkingServiceTypeConverter();
    VehicleMapper vehicleMapper = new VehicleMapper(parkingServiceTypeConverter);

    parkingManagerService = Mockito.mock(ParkingManagerService.class);
    checkInOrchestrator = new CheckInOrchestrator(parkingManagerService, vehicleManagerService, vehicleMapper, parkingSlotDao, parkingLogsDao);
  }

  @Test
  public void testCheckIn() throws NoSlotAvailableException, EntityNotFoundException {
    Order checkInOrder = TestUtils.getTestOrderDOForCheckIn();

    Parking parking = TestUtils.getTestParking();
    Mockito.when(parkingManagerService.find(checkInOrder.getParkingId())).thenReturn(parking);

    Optional<ParkingSlotEntity> anySlot = TestUtils.getTestParkingSlots().stream().findAny();
    Mockito.when(parkingSlotDao.findById(any())).thenReturn(anySlot);

    VehicleEntity vehicleEntity = TestUtils.getTestVehicleEntity();
    ParkingLogEntity newLog = checkInOrchestrator.createParkingLogEntity(checkInOrder, vehicleEntity);

    assertNotNull(newLog);
    assertEquals(checkInOrder.getTimeStampIn(), newLog.getTimeStampIn());
    // anySlot should contain always something
    assertEquals(anySlot.get(), newLog.getParkingSlotEntity());
    assertEquals(vehicleEntity, newLog.getVehicleEntity());
  }
}