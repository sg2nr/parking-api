package com.parking.parkingapi.service;

import com.parking.parkingapi.exception.ParkingApiException;
import com.parking.parkingapi.model.common.ParkingApiDO;

/**
 * Orchestrators are services that perform operations which are more
 * complex than usual CRUD ones.
 *
 * Orchestrators calls different ManagerServices and DAOs.
 *
 * @param <T>
 *    The Domain object handled by the orchestrator
 */
public interface Orchestrator<T extends ParkingApiDO> {

  /**
   * Start executing the orchestrator
   *
   * @param t
   * @return
   * @throws Exception
   */
  T run(T t) throws ParkingApiException;
}
