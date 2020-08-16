package com.parking.parkingapi.model.parking;

import com.parking.parkingapi.model.common.ParkingApiDO;
import com.parking.parkingapi.model.parkingslot.ParkingSlot;
import com.parking.parkingapi.model.pricing.PricingPolicy;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * The model representing a Parking lot.
 */
@Getter
@Setter
public class Parking implements ParkingApiDO {

  private long id;

  private String name;

  private String address;

  private String city;

  private List<ParkingSlot> parkingSlots;

  private Long pricingPolicyRequestId;

  private PricingPolicy pricingPolicy;
}
