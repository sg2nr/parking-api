package com.parking.parkingapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Check-out failure.")
public class TemporaryDataInconsistencyException extends Exception {

  public TemporaryDataInconsistencyException(String message) {
    super(message);
  }

}
