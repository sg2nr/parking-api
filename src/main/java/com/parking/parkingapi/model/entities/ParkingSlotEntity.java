package com.parking.parkingapi.model.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "parking_slots")
@Getter
@Setter
public class ParkingSlotEntity {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  long id;

  @ManyToOne
  @JoinColumn(name = "parking_id")
  private ParkingEntity parkingEntity;

  @Column(name = "slot_number")
  long slotNumber;

  @Column(name = "vehicle_allowed")
  private EngineType vehicleAllowed;

  @OneToMany(mappedBy = "parkingSlotEntity")
  private List<ParkingLogEntity> parkingLogEntities;

  public ParkingSlotEntity() {
  }

  public ParkingSlotEntity(long id, ParkingEntity parkingEntity, long slotNumber, EngineType vehicleAllowed, List<ParkingLogEntity> parkingLogEntities) {
    this.id = id;
    this.parkingEntity = parkingEntity;
    this.slotNumber = slotNumber;
    this.vehicleAllowed = vehicleAllowed;
    this.parkingLogEntities = parkingLogEntities;
  }
}
