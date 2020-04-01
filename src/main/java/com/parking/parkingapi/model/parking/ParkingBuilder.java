package com.parking.parkingapi.model.parking;

import com.parking.parkingapi.model.parkingslot.ParkingSlot;
import com.parking.parkingapi.model.pricing.PricingPolicy;

import java.util.List;

public final class ParkingBuilder {

  private long id;

  private String name;

  private String address;

  private String city;

  private List<ParkingSlot> parkingSlots;

  private Long pricingPolicyRequestId;

  private PricingPolicy pricingPolicy;

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

  public ParkingBuilder withParkingSlots(List<ParkingSlot> parkingSlots) {
    this.parkingSlots = parkingSlots;
    return this;
  }

  public ParkingBuilder withPricingPolicyRequestId(Long pricingPolicyRequestId) {
    this.pricingPolicyRequestId = pricingPolicyRequestId;
    return this;
  }

  public ParkingBuilder withPricingPolicy(PricingPolicy pricingPolicy) {
    this.pricingPolicy = pricingPolicy;
    return this;
  }

  public Parking build() {
    Parking parking = new Parking();
    parking.setId(id);
    parking.setName(name);
    parking.setAddress(address);
    parking.setCity(city);
    parking.setParkingSlots(parkingSlots);
    parking.setPricingPolicyRequestId(pricingPolicyRequestId);
    parking.setPricingPolicy(pricingPolicy);
    return parking;
  }
}
