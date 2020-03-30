package com.parking.parkingapi.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "vehicles")
public class VehicleEntity {

  @Id
  private String plate;

  @Column(name = "vehicle_engine_type", nullable = false)
  private VehiclePoweredType engineType;

  @OneToMany(mappedBy = "vehicleEntity")
  private List<ParkingLogEntity> parkingLogEntities;

  public VehicleEntity() {
  }

  public VehicleEntity(String plate, VehiclePoweredType engineType) {
    this.plate = plate;
    this.engineType = engineType;
  }

  public String getPlate() {
    return plate;
  }

  public void setPlate(String plate) {
    this.plate = plate;
  }

  public VehiclePoweredType getEngineType() {
    return engineType;
  }

  public void setEngineType(VehiclePoweredType engineType) {
    this.engineType = engineType;
  }

  public List<ParkingLogEntity> getParkingLogEntities() {
    return parkingLogEntities;
  }

  public void setParkingLogEntities(List<ParkingLogEntity> parkingLogEntities) {
    this.parkingLogEntities = parkingLogEntities;
  }
}
