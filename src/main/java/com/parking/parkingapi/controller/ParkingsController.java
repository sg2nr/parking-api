package com.parking.parkingapi.controller;

import com.parking.parkingapi.dto.Parking;
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
public class ParkingsController {

  private final ParkingBusinessService parkingBusinessService;

  @Autowired
  public ParkingsController(ParkingBusinessService parkingBusinessService) {
    this.parkingBusinessService = parkingBusinessService;
  }

  @GetMapping("/{parkingId}")
  public Parking retrieveParking(@Valid @PathVariable long parkingId) throws EntityNotFoundException {
    return parkingBusinessService.find(parkingId);
  }

  @GetMapping
  public List<Parking> findAllParkings() {
    return parkingBusinessService.findAll();
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Parking createParking(@RequestBody Parking parking) throws EntityCreationViolation {
    return parkingBusinessService.create(parking);
  }

  @DeleteMapping("/{parkingId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteParking(@Valid @PathVariable long parkingId) throws EntityNotFoundException {
    parkingBusinessService.delete(parkingId);
  }
}
