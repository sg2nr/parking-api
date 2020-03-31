package com.parking.parkingapi.controller;

import com.parking.parkingapi.controller.mapper.ParkingJsonMapper;
import com.parking.parkingapi.model.parking.Parking;
import com.parking.parkingapi.model.parking.request.CreateParkingRequest;
import com.parking.parkingapi.model.parking.response.DisplayParkingResponse;
import com.parking.parkingapi.exception.EntityCreationViolation;
import com.parking.parkingapi.exception.EntityNotFoundException;
import com.parking.parkingapi.service.ParkingBusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("parkings")
public class ParkingController {

  private final ParkingBusinessService businessService;

  private final ParkingJsonMapper mapper;

  @Autowired
  public ParkingController(ParkingBusinessService businessService, ParkingJsonMapper mapper) {
    this.businessService = businessService;
    this.mapper = mapper;
  }

  @GetMapping("/{parkingId}")
  public DisplayParkingResponse retrieveParking(@Valid @PathVariable long parkingId) throws EntityNotFoundException {
    Parking parking = businessService.find(parkingId);
    return mapper.mapToResponse(parking);
  }

  @GetMapping
  public List<Parking> findAllParkings() {
    return businessService.findAll();
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Parking createParking(@RequestBody CreateParkingRequest request) throws EntityCreationViolation {
    Parking requestedParking = mapper.mapToDto(request);
    return businessService.create(requestedParking);
  }

  @DeleteMapping("/{parkingId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteParking(@Valid @PathVariable long parkingId) throws EntityNotFoundException {
    businessService.delete(parkingId);
  }
}
