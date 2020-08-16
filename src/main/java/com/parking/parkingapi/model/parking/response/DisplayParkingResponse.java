package com.parking.parkingapi.model.parking.response;

import com.parking.parkingapi.model.pricing.PricingPolicy;
import lombok.Getter;
import lombok.Setter;

/**
 * Response with Parking information for display operations
 */
@Getter
@Setter
public class DisplayParkingResponse {

  private long id;

  private String name;

  private String address;

  private String city;

  private ParkingStatistics statistics;

  private PricingPolicy pricingPolicy;
}
