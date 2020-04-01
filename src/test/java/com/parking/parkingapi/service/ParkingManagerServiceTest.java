package com.parking.parkingapi.service;

import com.parking.parkingapi.TestUtils;
import com.parking.parkingapi.dao.ParkingDao;
import com.parking.parkingapi.dao.ParkingLogsDao;
import com.parking.parkingapi.dao.ParkingSlotDao;
import com.parking.parkingapi.exception.EntityNotFoundException;
import com.parking.parkingapi.model.entities.ParkingEntity;
import com.parking.parkingapi.model.entities.ParkingSlotEntity;
import com.parking.parkingapi.model.parking.Parking;
import com.parking.parkingapi.service.mapper.VehicleMapper;
import com.parking.parkingapi.service.mapper.ParkingMapper;
import com.parking.parkingapi.service.mapper.ParkingServiceTypeConverter;
import com.parking.parkingapi.service.mapper.ParkingSlotMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.TestInstance.Lifecycle;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(SpringExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
class ParkingManagerServiceTest {

  ParkingManagerService parkingBusinessService;

  ParkingDao parkingDao;

  ParkingSlotDao parkingSlotDao;

  ParkingLogsDao parkingLogsDao;

  @BeforeAll
  public void setup() {
    parkingDao = Mockito.mock(ParkingDao.class);
    parkingSlotDao = Mockito.mock(ParkingSlotDao.class);
    parkingLogsDao = Mockito.mock(ParkingLogsDao.class);

    ParkingServiceTypeConverter parkingServiceTypeConverter = new ParkingServiceTypeConverter();
    VehicleMapper vehicleMapper = new VehicleMapper(parkingServiceTypeConverter);
    ParkingSlotMapper slotMapper = new ParkingSlotMapper(vehicleMapper, parkingServiceTypeConverter);
    ParkingMapper mapper = new ParkingMapper(slotMapper);

    parkingBusinessService = new ParkingManagerService(parkingDao, parkingSlotDao, parkingLogsDao,
        mapper, slotMapper);
  }

  @Test
  public void testFindById() throws EntityNotFoundException {
    ParkingEntity testParkingEntity = TestUtils.getTestParkingEntity();
    Mockito.when(parkingDao.findById(anyLong())).thenReturn(of(testParkingEntity));

    List<ParkingSlotEntity> testParkingSlots = TestUtils.getTestParkingSlots();
    Mockito.when(parkingSlotDao.findByParkingEntity(testParkingEntity)).thenReturn(testParkingSlots);

    Mockito.when(parkingLogsDao.findAll()).thenReturn(new ArrayList<>());

    Parking parking = parkingBusinessService.find(TestUtils.PARKING_ID_1);

    assertEquals(TestUtils.PARKING_ID_1, parking.getId());
    assertEquals(TestUtils.PARKING_NAME_1, parking.getName());
    assertEquals(TestUtils.PARKING_ADDRESS_1, parking.getAddress());
    assertEquals(TestUtils.PARKING_CITY_1, parking.getCity());
  }

  @Test
  public void testFindParkingNotExists() {
    Mockito.when(parkingDao.findById(TestUtils.PARKING_ID_1)).thenReturn(Optional.empty());
    Mockito.when(parkingSlotDao.findByParkingEntity(any())).thenReturn(new ArrayList<>());
    Mockito.when(parkingLogsDao.findAll()).thenReturn(new ArrayList<>());

    Exception exception = assertThrows(EntityNotFoundException.class,
        () -> parkingBusinessService.find(TestUtils.PARKING_ID_1));

    String expectedMessage = "Impossible to retrieve parking with id: 1.";
    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  public void testDeleteParkingNotExists() {
    Mockito.when(parkingDao.findById(anyLong())).thenReturn(Optional.empty());

    Exception exception = assertThrows(EntityNotFoundException.class,
        () -> parkingBusinessService.delete(TestUtils.PARKING_ID_1));

    String expectedMessage = "Impossible to retrieve parking with id: 1.";
    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expectedMessage));
  }
}