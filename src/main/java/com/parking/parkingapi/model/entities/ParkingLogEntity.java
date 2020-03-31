package com.parking.parkingapi.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.ZonedDateTime;

@Entity
@Table(name = "parking_logs")
public class ParkingLogEntity {

  @Id
  private Long id;

  @Column(name = "timestamp_in", nullable = false)
  private ZonedDateTime timeStampIn;

  @Column(name = "timestamp_out", nullable = true)
  private ZonedDateTime timeStampOut;

  @ManyToOne
  @JoinColumn(name = "vehicle_plate")
  private VehicleEntity vehicleEntity;

  @ManyToOne
  @JoinColumn(name = "slot_id")
  private ParkingSlotEntity parkingSlotEntity;

  public ParkingLogEntity() {
  }

  public ParkingLogEntity(Long id, ZonedDateTime timeStampIn, ZonedDateTime timeStampOut, VehicleEntity vehicleEntity, ParkingSlotEntity parkingSlotEntity) {
    this.id = id;
    this.timeStampIn = timeStampIn;
    this.timeStampOut = timeStampOut;
    this.vehicleEntity = vehicleEntity;
    this.parkingSlotEntity = parkingSlotEntity;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public ZonedDateTime getTimeStampIn() {
    return timeStampIn;
  }

  public void setTimeStampIn(ZonedDateTime timeStampIn) {
    this.timeStampIn = timeStampIn;
  }

  public ZonedDateTime getTimeStampOut() {
    return timeStampOut;
  }

  public void setTimeStampOut(ZonedDateTime timeStampOut) {
    this.timeStampOut = timeStampOut;
  }

  public VehicleEntity getVehicleEntity() {
    return this.vehicleEntity;
  }

  public void setVehicleEntity(VehicleEntity vehicleEntity) {
    this.vehicleEntity = vehicleEntity;
  }

  public ParkingSlotEntity getParkingSlotEntity() {
    return parkingSlotEntity;
  }

  public void setParkingSlotEntity(ParkingSlotEntity parkingSlotEntity) {
    this.parkingSlotEntity = parkingSlotEntity;
  }
}
