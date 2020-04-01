package com.parking.parkingapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid input data")
public class InvalidInputDataException extends Exception {

  public InvalidInputDataException(String message) {
    super(message);
  }
}
