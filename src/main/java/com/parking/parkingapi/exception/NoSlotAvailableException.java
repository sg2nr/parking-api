package com.parking.parkingapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "No slot available.")
public class NoSlotAvailableException extends ParkingApiException {

  public NoSlotAvailableException(String message) {
    super(message);
  }
}
