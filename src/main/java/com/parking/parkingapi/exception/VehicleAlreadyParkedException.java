package com.parking.parkingapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Vehicle is currently parked.")
public class VehicleAlreadyParkedException extends ParkingApiException {

  public VehicleAlreadyParkedException(String message) {
    super(message);
  }
}
