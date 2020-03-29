package com.parking.parkingapi.service;

import com.parking.parkingapi.dto.ParkingApiDto;
import com.parking.parkingapi.exception.EntityNotFoundException;
import com.parking.parkingapi.exception.EntityCreationViolation;

import java.io.Serializable;
import java.util.List;

/**
 * Parking-API business services have to implement this interface.
 * BusinessService interface provides most common CRUD operations.
 *
 * @param <T>
 *   The corresponding DTO
 * @param <ID>
 *   The entity identifier
 */
public interface BusinessService<T extends ParkingApiDto, ID extends Serializable> {

  T find(ID id) throws EntityNotFoundException;

  T create(T newElement) throws EntityCreationViolation;

  List<T> findAll();

  void delete(ID id) throws EntityNotFoundException;
}
