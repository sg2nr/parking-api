package com.parking.parkingapi.model.parking.request;

import com.parking.parkingapi.model.common.ParkingServiceType;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * Request used for parking creation.
 */
@Getter
@Setter
public class CreateParkingRequest {

  private String name;

  private String address;

  private String city;

  private Map<ParkingServiceType, Integer> requestedSlots;

  private Long pricingPolicyId;
}
