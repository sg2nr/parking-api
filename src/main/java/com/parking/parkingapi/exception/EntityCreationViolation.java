package com.parking.parkingapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Request does not respect data constraints.")
public class EntityCreationViolation extends ParkingApiException {

  public EntityCreationViolation(String message) {
    super(message);
  }
}
