package com.parking.parkingapi.model.parking.response;

import com.parking.parkingapi.model.pricing.PricingPolicy;

/**
 * Builder for Parking DTO in Response
 */
public final class DisplayParkingResponseBuilder {

  private long id;

  private String name;

  private String address;

  private String city;

  private ParkingStatistics statistics;

  private PricingPolicy pricingPolicy;

  private DisplayParkingResponseBuilder() {
  }

  public static DisplayParkingResponseBuilder builder() {
    return new DisplayParkingResponseBuilder();
  }

  public DisplayParkingResponseBuilder withId(long id) {
    this.id = id;
    return this;
  }

  public DisplayParkingResponseBuilder withName(String name) {
    this.name = name;
    return this;
  }

  public DisplayParkingResponseBuilder withAddress(String address) {
    this.address = address;
    return this;
  }

  public DisplayParkingResponseBuilder withCity(String city) {
    this.city = city;
    return this;
  }

  public DisplayParkingResponseBuilder withStatistics(ParkingStatistics statistics) {
    this.statistics = statistics;
    return this;
  }

  public DisplayParkingResponseBuilder withPricingPolicy(PricingPolicy pricingPolicy) {
    this.pricingPolicy = pricingPolicy;
    return this;
  }

  public DisplayParkingResponse build() {
    DisplayParkingResponse displayParkingResponse = new DisplayParkingResponse();
    displayParkingResponse.setId(id);
    displayParkingResponse.setName(name);
    displayParkingResponse.setAddress(address);
    displayParkingResponse.setCity(city);
    displayParkingResponse.setStatistics(statistics);
    displayParkingResponse.setPricingPolicy(pricingPolicy);
    return displayParkingResponse;
  }
}
