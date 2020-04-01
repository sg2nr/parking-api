package com.parking.parkingapi.service;

import com.parking.parkingapi.model.common.ParkingApiDO;
import com.parking.parkingapi.exception.EntityNotFoundException;
import com.parking.parkingapi.exception.EntityCreationViolation;

import java.io.Serializable;
import java.util.List;

/**
 * Parking-API business services have to implement this interface.
 * ManagerService interface provides most common CRUD operations.
 *
 * @param <T>
 *   The corresponding Domain object
 * @param <ID>
 *   The entity identifier
 */
public interface ManagerService<T extends ParkingApiDO, ID extends Serializable> {

  T find(ID id) throws EntityNotFoundException;

  T create(T newElement) throws EntityCreationViolation;

  List<T> findAll();

  void delete(ID id) throws EntityNotFoundException;
}
