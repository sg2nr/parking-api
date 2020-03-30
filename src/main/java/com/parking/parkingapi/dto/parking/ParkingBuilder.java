package com.parking.parkingapi.dto.parking;

import com.parking.parkingapi.dto.common.ParkingServiceType;

import java.util.Map;

/**
 * Builder class for {@link Parking}.
 */
public final class ParkingBuilder {

  private long id;

  private String name;

  private String address;

  private String city;

  private ParkingStatus status;

  private Map<ParkingServiceType, ParkingStatus> statusPerType;

  private ParkingBuilder() {
  }

  public static ParkingBuilder builder() {
    return new ParkingBuilder();
  }

  public ParkingBuilder withId(long id) {
    this.id = id;
    return this;
  }

  public ParkingBuilder withName(String name) {
    this.name = name;
    return this;
  }

  public ParkingBuilder withAddress(String address) {
    this.address = address;
    return this;
  }

  public ParkingBuilder withCity(String city) {
    this.city = city;
    return this;
  }

  public ParkingBuilder withStatus(ParkingStatus status) {
    this.status = status;
    return this;
  }

  public ParkingBuilder withStatusPerType(Map<ParkingServiceType, ParkingStatus> statusPerType) {
    this.statusPerType = statusPerType;
    return this;
  }

  public Parking build() {
    return new Parking(id, name, address, city, status, statusPerType);
  }
}
