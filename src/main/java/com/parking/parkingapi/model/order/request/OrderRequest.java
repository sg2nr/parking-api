package com.parking.parkingapi.model.order.request;

import com.parking.parkingapi.model.common.ParkingServiceType;

public class OrderRequest {

  private String carPlate;

  private ParkingServiceType serviceRequested;

  public OrderRequest() {
  }

  public String getCarPlate() {
    return carPlate;
  }

  public void setCarPlate(String carPlate) {
    this.carPlate = carPlate;
  }

  public ParkingServiceType getServiceRequested() {
    return serviceRequested;
  }

  public void setServiceRequested(ParkingServiceType serviceRequested) {
    this.serviceRequested = serviceRequested;
  }
}
