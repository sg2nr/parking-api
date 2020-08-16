package com.parking.parkingapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parking.parkingapi.model.parking.response.DisplayParkingResponse;
import com.parking.parkingapi.service.ParkingManagerService;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
class ParkingControllerTest {

  private static final String PARKINGS_URL = "/parkings";

  private static final String PARKING_ID_1 = "1";

  private static final String PARKING_ID_1_EXPECTED_JSON_FILENAME =
      "com/parking/parkingapi/controller/get_parking_1_expected_response.json";

  private static final String PARKING_ID_NOT_EXISTING = "99999";

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ParkingManagerService parkingBusinessService;

  @Test
  void getExistingParking() throws Exception {

    MvcResult result = mockMvc.perform(get(PARKINGS_URL + "/" + PARKING_ID_1)).andReturn();

    int status = result.getResponse().getStatus();
    assertEquals(HttpStatus.OK.value(), status);

    // Actual response
    String actualResponse = result.getResponse().getContentAsString();
    assertFalse(StringUtils.isBlank(actualResponse));
    ObjectMapper mapper = new ObjectMapper();
    DisplayParkingResponse parkingInResponse = mapper.readValue(actualResponse, DisplayParkingResponse.class);
    assertNotNull(parkingInResponse);

    // expected response
    Path path = Paths.get(ClassLoader.getSystemResource(PARKING_ID_1_EXPECTED_JSON_FILENAME).toURI());
    String expectedResponse = new String(Files.readAllBytes(path));

    JSONAssert.assertEquals(expectedResponse, actualResponse, JSONCompareMode.LENIENT);
  }

  @Test
  void getNotExistingParking() throws Exception {
    MvcResult result = mockMvc.perform(get(PARKINGS_URL + "/" + PARKING_ID_NOT_EXISTING)).andReturn();

    int status = result.getResponse().getStatus();
    assertEquals(HttpStatus.BAD_REQUEST.value(), status);

    String actualResponse = result.getResponse().getContentAsString();
    assertNotNull(actualResponse);
    assertTrue(StringUtils.isBlank(actualResponse));
  }
}