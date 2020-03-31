package com.parking.parkingapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parking.parkingapi.TestUtils;
import com.parking.parkingapi.exception.EntityNotFoundException;
import com.parking.parkingapi.model.parking.Parking;
import com.parking.parkingapi.model.parking.response.DisplayParkingResponse;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
class ParkingControllerTest {

  private static final String PARKINGS_URL = "/parkings";

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ParkingBusinessService parkingBusinessService;

  @Test
  public void getExistingParking() throws Exception {
    Parking parking = TestUtils.getTestParking();
    long id1 = TestUtils.PARKING_ID_1;

    Mockito.when(parkingBusinessService.find(id1)).thenReturn(parking);

    MvcResult result = mockMvc.perform(get(PARKINGS_URL + "/" + id1)).andReturn();

    int status = result.getResponse().getStatus();
    assertEquals(HttpStatus.OK.value(), status);

    String json = result.getResponse().getContentAsString();
    ObjectMapper mapper = new ObjectMapper();
    DisplayParkingResponse parkingInResponse = mapper.readValue(json, DisplayParkingResponse.class);

    assertNotNull(parkingInResponse);
    assertEquals(parking.getId(), parkingInResponse.getId());
    assertEquals(parking.getName(), parkingInResponse.getName());
    assertEquals(parking.getAddress(), parkingInResponse.getAddress());
    assertEquals(parking.getCity(), parkingInResponse.getCity());
  }

  @Test
  public void getNotExistingParking() throws Exception {
    long id1 = TestUtils.PARKING_ID_1;

    Mockito.when(parkingBusinessService.find(Mockito.anyLong())).thenThrow(new EntityNotFoundException("Not found"));

    MvcResult result = mockMvc.perform(get(PARKINGS_URL + "/" + id1)).andReturn();

    int status = result.getResponse().getStatus();
    assertEquals(HttpStatus.BAD_REQUEST.value(), status);

    String json = result.getResponse().getContentAsString();
    assertNotNull(json);
    assertTrue(StringUtils.isBlank(json));
  }
}