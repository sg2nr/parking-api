package com.parking.parkingapi.controller.mapper;

import com.parking.parkingapi.TestUtils;
import com.parking.parkingapi.model.parking.Parking;
import com.parking.parkingapi.model.parking.response.DisplayParkingResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
class ParkingJsonMapperTest {

  private ParkingJsonMapper parkingJsonMapper;

  @BeforeAll
  public void setUp() {
    parkingJsonMapper = new ParkingJsonMapper();
  }

  @Test
  void testMappingToResponse() {
    Parking parking = TestUtils.getTestParking();

    DisplayParkingResponse displayParkingResponse = parkingJsonMapper.mapToResponse(parking);

    assertNotNull(displayParkingResponse);
    assertEquals(TestUtils.PARKING_ID_1, displayParkingResponse.getId());
    assertEquals(TestUtils.PARKING_NAME_1, displayParkingResponse.getName());
    assertEquals(TestUtils.PARKING_ADDRESS_1, displayParkingResponse.getAddress());
    assertEquals(TestUtils.PARKING_CITY_1, displayParkingResponse.getCity());
    int totalExpected = TestUtils.TOTAL_STANDARD_SLOTS + TestUtils.TOTAL_20KW_SLOTS + TestUtils.TOTAL_50KW_SLOTS;
    long totalActual = displayParkingResponse.getStatistics().getParkingStatus().getTotalSlots();
    assertEquals(totalExpected, totalActual);
  }

}