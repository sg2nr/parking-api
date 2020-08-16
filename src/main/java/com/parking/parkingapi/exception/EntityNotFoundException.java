package com.parking.parkingapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Entity with requested id does not exist.")
public class EntityNotFoundException extends ParkingApiException {

  public EntityNotFoundException(String message) {
    super(message);
  }
}
