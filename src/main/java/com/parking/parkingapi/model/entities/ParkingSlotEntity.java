package com.parking.parkingapi.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "parking_slots")
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

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public ParkingEntity getParkingEntity() {
    return parkingEntity;
  }

  public void setParkingEntity(ParkingEntity parkingEntity) {
    this.parkingEntity = parkingEntity;
  }

  public long getSlotNumber() {
    return slotNumber;
  }

  public void setSlotNumber(long slotNumber) {
    this.slotNumber = slotNumber;
  }

  public EngineType getVehicleAllowed() {
    return vehicleAllowed;
  }

  public void setVehicleAllowed(EngineType vehicleAllowed) {
    this.vehicleAllowed = vehicleAllowed;
  }

  public List<ParkingLogEntity> getParkingLogEntities() {
    return parkingLogEntities;
  }

  public void setParkingLogEntities(List<ParkingLogEntity> parkingLogEntities) {
    this.parkingLogEntities = parkingLogEntities;
  }
}
