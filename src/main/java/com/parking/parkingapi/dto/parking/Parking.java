package com.parking.parkingapi.dto.parking;

import com.parking.parkingapi.dto.ParkingApiDto;
import com.parking.parkingapi.dto.common.ParkingServiceType;

import java.util.Map;

/**
 * Data Transfer Object representing a Parking lot
 */
public class Parking implements ParkingApiDto {

  private long id;

  private String name;

  private String address;

  private String city;

  private ParkingStatus status;

  private Map<ParkingServiceType, ParkingStatus> statusPerType;

  public Parking() {
  }

  public Parking(long id, String name, String address, String city, ParkingStatus status, Map<ParkingServiceType, ParkingStatus> statusPerType) {
    this.id = id;
    this.name = name;
    this.address = address;
    this.city = city;
    this.status = status;
    this.statusPerType = statusPerType;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
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

  public ParkingStatus getStatus() {
    return status;
  }

  public void setStatus(ParkingStatus status) {
    this.status = status;
  }

  public Map<ParkingServiceType, ParkingStatus> getStatusPerType() {
    return statusPerType;
  }

  public void setStatusPerType(Map<ParkingServiceType, ParkingStatus> statusPerType) {
    this.statusPerType = statusPerType;
  }
}
