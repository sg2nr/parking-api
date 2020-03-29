package com.parking.parkingapi.dto;

/**
 * Data Transfer Object representing a Parking lot
 */
public class Parking implements ParkingApiDto {

  private long id;

  private String name;

  private String address;

  private String city;

  public Parking() {
  }

  public Parking(long id, String name, String address, String city) {
    this.id = id;
    this.name = name;
    this.address = address;
    this.city = city;
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
}
