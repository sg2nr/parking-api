package com.parking.parkingapi.dto.parkingslot;

import com.parking.parkingapi.dto.car.Vehicle;

import java.util.Optional;

/**
 * This interface exposes the operations performed on a parking slot.
 * @param <T>
 *    It represents a vehicle
 */
public interface ParkingSlotService<T extends Vehicle> {

  /**
   * The input vehicle is parked in.
   * @param vehicle
   */
  void insert(T vehicle);

  /**
   * It makes the spot empty.
   */
  void removeVehicle();

  /**
   * It returns the parked vehicle, if any.
   *
   * @return
   *    It returns an Optional containing, or not, the vehicle.
   */
  Optional<T> getParkedVehicle();
}
