package com.parking.parkingapi.service.mapper;

import com.parking.parkingapi.dto.Parking;
import com.parking.parkingapi.entities.ParkingEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParkingMapperTest {

  private static final long PARKING_ID = 1;

  private static final String PARKING_NAME = "Parking de la Baie";

  private static final String PARKING_ADDRESS = "Boulevard de la Croisette";

  private static final String PARKING_CITY = "Cannes";

  private static ParkingMapper parkingMapper;

  @BeforeAll
  static void setUp() {
    parkingMapper = new ParkingMapper();
  }

  @Test
  void testMappingToDto() {
    ParkingEntity parkingEntity = new ParkingEntity();
    parkingEntity.setId(PARKING_ID);
    parkingEntity.setName(PARKING_NAME);
    parkingEntity.setAddress(PARKING_ADDRESS);
    parkingEntity.setCity(PARKING_CITY);

    Parking parking = parkingMapper.mapToDto(parkingEntity);

    assertNotNull(parking);
    assertEquals(PARKING_ID, parking.getId());
    assertEquals(PARKING_NAME, parking.getName());
    assertEquals(PARKING_ADDRESS, parking.getAddress());
    assertEquals(PARKING_CITY, parking.getCity());
  }

  @Test
  void mapToEntity() {
    Parking parking = new Parking(PARKING_ID, PARKING_NAME, PARKING_ADDRESS, PARKING_CITY);

    ParkingEntity parkingEntity = parkingMapper.mapToEntity(parking);

    assertNotNull(parkingEntity);
    assertEquals(PARKING_ID, parkingEntity.getId());
    assertEquals(PARKING_NAME, parkingEntity.getName());
    assertEquals(PARKING_ADDRESS, parkingEntity.getAddress());
    assertEquals(PARKING_CITY, parkingEntity.getCity());
  }
}