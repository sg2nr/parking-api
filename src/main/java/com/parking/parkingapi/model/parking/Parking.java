package com.parking.parkingapi.model.parking;

import com.parking.parkingapi.dto.ParkingApiDto;
import com.parking.parkingapi.model.parkingslot.ParkingSlot;
import com.parking.parkingapi.model.pricing.PricingPolicy;

import java.util.List;

/**
 * Data Transfer Object representing a Parking lot.
 */
public class Parking implements ParkingApiDto {

  private long id;

  private String name;

  private String address;

  private String city;

  private List<ParkingSlot> parkingSlots;

  private PricingPolicy pricingPolicy;

  public Parking() {
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

  public List<ParkingSlot> getParkingSlots() {
    return parkingSlots;
  }

  public void setParkingSlots(List<ParkingSlot> parkingSlots) {
    this.parkingSlots = parkingSlots;
  }

  public PricingPolicy getPricingPolicy() {
    return pricingPolicy;
  }

  public void setPricingPolicy(PricingPolicy pricingPolicy) {
    this.pricingPolicy = pricingPolicy;
  }
}
