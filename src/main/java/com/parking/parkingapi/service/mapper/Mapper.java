package com.parking.parkingapi.service.mapper;

import com.parking.parkingapi.dto.ParkingApiDto;

/**
 * Interface to be implemented by classes mapping from Data Transfer Object to an Entity object and
 * vice-versa.
 *
 * @param <T>
 *   The DTO
 * @param <U>
 *   The Entity object
 */
public interface Mapper<T extends ParkingApiDto, U> {

  T mapToDto(U entity);

  U mapToEntity(T dto);
}
