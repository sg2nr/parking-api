package com.parking.parkingapi.model.parking.request;

import com.parking.parkingapi.model.common.ParkingServiceType;

import java.util.Map;

/**
 * Request used for parking creation.
 */
public class CreateParkingRequest {

  private String name;

  private String address;

  private String city;

  private Map<ParkingServiceType, Integer> requestedSlots;

  public CreateParkingRequest() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public Map<ParkingServiceType, Integer> getRequestedSlots() {
    return requestedSlots;
  }

  public void setRequestedSlots(Map<ParkingServiceType, Integer> requestedSlots) {
    this.requestedSlots = requestedSlots;
  }
}
