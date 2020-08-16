package com.parking.parkingapi.model.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.ZonedDateTime;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "parking_logs")
@Getter
@Setter
public class ParkingLogEntity {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(name = "timestamp_in", nullable = false)
  private ZonedDateTime timeStampIn;

  @Column(name = "timestamp_out")
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
}
