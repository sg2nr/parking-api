package com.parking.parkingapi.model.order.request;

import com.parking.parkingapi.model.common.ParkingServiceType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequest {

  private String carPlate;

  private ParkingServiceType serviceRequested;
}
