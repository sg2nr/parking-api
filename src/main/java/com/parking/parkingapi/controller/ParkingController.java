package com.parking.parkingapi.controller;

import com.parking.parkingapi.controller.mapper.ParkingJsonMapper;
import com.parking.parkingapi.exception.BadJsonRequestException;
import com.parking.parkingapi.exception.EntityCreationViolation;
import com.parking.parkingapi.exception.EntityNotFoundException;
import com.parking.parkingapi.model.parking.Parking;
import com.parking.parkingapi.model.parking.request.CreateParkingRequest;
import com.parking.parkingapi.model.parking.response.DisplayParkingResponse;
import com.parking.parkingapi.service.ParkingManagerService;
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

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("parkings")
public class ParkingController {

  private final ParkingManagerService parkingManagerService;

  private final ParkingJsonMapper mapper;

  @Autowired
  public ParkingController(ParkingManagerService parkingManagerService, ParkingJsonMapper mapper) {
    this.parkingManagerService = parkingManagerService;
    this.mapper = mapper;
  }

  @GetMapping("/{parkingId}")
  public DisplayParkingResponse retrieveParking(@Valid @PathVariable long parkingId) throws EntityNotFoundException {
    Parking parking = parkingManagerService.find(parkingId);
    return mapper.mapToResponse(parking);
  }

  @GetMapping
  public List<DisplayParkingResponse> findAllParkings() {
    List<Parking> parkingsRetrieved = parkingManagerService.findAll();
    return parkingsRetrieved.stream().map(mapper::mapToResponse).collect(toList());
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public DisplayParkingResponse createParking(@RequestBody CreateParkingRequest request)
      throws EntityCreationViolation, BadJsonRequestException {

    Parking requestedParking = mapper.mapToDto(request);
    Parking createdParking = parkingManagerService.create(requestedParking);
    return mapper.mapToResponse(createdParking);
  }

  @DeleteMapping("/{parkingId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteParking(@Valid @PathVariable long parkingId) throws EntityNotFoundException {
    parkingManagerService.delete(parkingId);
  }
}
