package com.parking.parkingapi.service;

import com.parking.parkingapi.model.common.ParkingApiDto;

/**
 * Orchestrators are service that perform operations which are more
 * complex than usual CRUD ones.
 *
 * Orchestrators calls different ManagerServices and DAOs.
 *
 * @param <T>
 *    The DTO handled by the orchestrator
 */
public interface Orchestrator<T extends ParkingApiDto> {

  /**
   * Start executing the orchestrator
   *
   * @param t
   * @return
   * @throws Exception
   */
  T run(T t) throws Exception;
}
