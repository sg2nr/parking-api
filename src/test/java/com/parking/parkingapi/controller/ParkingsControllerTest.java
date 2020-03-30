package com.parking.parkingapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parking.parkingapi.dto.parking.Parking;
import com.parking.parkingapi.dto.parking.ParkingStatus;
import com.parking.parkingapi.exception.EntityNotFoundException;
import com.parking.parkingapi.service.ParkingBusinessService;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
class ParkingsControllerTest {

  private static final String PARKINGS_URL = "/parkings";

  private static final long PARKING_ID_1 = 1;

  private static final String PARKING_NAME_1 = "Parking de la Baie";

  private static final String PARKING_ADDRESS_1 = "Boulevard de la Croisette";

  private static final String PARKING_CITY_1 = "Cannes";

  private static final long PARKING_ID_2 = 2;

  private static final String PARKING_NAME_2 = "Parking du Sud";

  private static final String PARKING_ADDRESS_2 = "Gare du Sud";

  private static final String PARKING_CITY_2 = "Nice";

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ParkingBusinessService parkingBusinessService;

  @Test
  public void getExistingParking() throws Exception {
    ParkingStatus overallStatus = new ParkingStatus(0, 0);
    Parking parking = new Parking(PARKING_ID_1, PARKING_NAME_1, PARKING_ADDRESS_1, PARKING_CITY_1, overallStatus, new HashMap<>());

    Mockito.when(parkingBusinessService.find(PARKING_ID_1)).thenReturn(parking);

    MvcResult result = mockMvc.perform(get(PARKINGS_URL + "/" + PARKING_ID_1)).andReturn();

    int status = result.getResponse().getStatus();
    assertEquals(HttpStatus.OK.value(), status);

    String json = result.getResponse().getContentAsString();
    ObjectMapper mapper = new ObjectMapper();
    Parking parkingInResponse = mapper.readValue(json, Parking.class);

    assertNotNull(parkingInResponse);
    assertEquals(parking.getId(), parkingInResponse.getId());
    assertEquals(parking.getName(), parkingInResponse.getName());
    assertEquals(parking.getAddress(), parkingInResponse.getAddress());
    assertEquals(parking.getCity(), parkingInResponse.getCity());
  }

  @Test
  public void getNotExistingParking() throws Exception {

    Mockito.when(parkingBusinessService.find(Mockito.anyLong())).thenThrow(new EntityNotFoundException("Not found"));

    MvcResult result = mockMvc.perform(get(PARKINGS_URL + "/" + PARKING_ID_1)).andReturn();

    int status = result.getResponse().getStatus();
    assertEquals(HttpStatus.BAD_REQUEST.value(), status);

    String json = result.getResponse().getContentAsString();
    assertNotNull(json);
    assertTrue(StringUtils.isBlank(json));
  }
}