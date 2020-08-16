package com.parking.parkingapi.model.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "vehicles")
@Getter
@Setter
@EqualsAndHashCode
public class VehicleEntity {

  @Id
  private String plate;

  @Column(name = "vehicle_engine_type", nullable = false)
  private EngineType engineType;

  @OneToMany(mappedBy = "vehicleEntity")
  private List<ParkingLogEntity> parkingLogEntities;

  public VehicleEntity() {
  }

  public VehicleEntity(String plate, EngineType engineType) {
    this.plate = plate;
    this.engineType = engineType;
  }
}
