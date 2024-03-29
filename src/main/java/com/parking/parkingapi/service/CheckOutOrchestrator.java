package com.parking.parkingapi.service;

import com.parking.parkingapi.dao.ParkingLogsDao;
import com.parking.parkingapi.exception.CheckOutAlreadyPerformedException;
import com.parking.parkingapi.exception.NoPricingPolicyFound;
import com.parking.parkingapi.exception.OrderNotFoundException;
import com.parking.parkingapi.exception.TemporaryDataInconsistencyException;
import com.parking.parkingapi.model.entities.ParkingEntity;
import com.parking.parkingapi.model.entities.ParkingLogEntity;
import com.parking.parkingapi.model.order.Order;
import com.parking.parkingapi.model.order.OrderBuilder;
import com.parking.parkingapi.model.pricing.Price;
import com.parking.parkingapi.model.pricing.PricingPolicy;
import com.parking.parkingapi.model.vehicle.Vehicle;
import com.parking.parkingapi.service.mapper.PricingPolicyFactory;
import com.parking.parkingapi.service.mapper.VehicleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Optional;

/**
 * This service is responsible to remove a vehicle from a parking slot and compute the final price.
 */
@Service
public class CheckOutOrchestrator implements Orchestrator<Order> {

  private static final Logger LOG = LoggerFactory.getLogger(CheckOutOrchestrator.class);

  private static final String NO_POLICY_FOUND_ERROR_MESSAGE =
      "No pricing policy found for parking id: %s";

  private static final String ORDER_NOT_FOUND_ERROR_MESSAGE = "Order id: %s not found";

  private static final String CHECKOUT_ALREADY_PERFOMED_ERROR_MESSAGE =
      "Checkout already perfomed.";

  private final ParkingLogsDao parkingLogsDao;

  private final VehicleMapper vehicleMapper;

  @Autowired
  public CheckOutOrchestrator(ParkingLogsDao parkingLogsDao, VehicleMapper vehicleMapper) {
    this.parkingLogsDao = parkingLogsDao;
    this.vehicleMapper = vehicleMapper;
  }

  @Override
  public Order run(Order order)
      throws OrderNotFoundException, NoPricingPolicyFound, TemporaryDataInconsistencyException,
          CheckOutAlreadyPerformedException {

    long orderId = order.getOrderId();

    ParkingLogEntity savedLog = checkOut(orderId);

    Price amount = computeAmount(savedLog);

    Vehicle vehicle = vehicleMapper.mapToVehicle(savedLog.getVehicleEntity());
    ParkingEntity parkingEntity = savedLog.getParkingSlotEntity().getParkingEntity();

    return OrderBuilder.builder()
        .withOrderId(orderId)
        .withTimeStampIn(savedLog.getTimeStampIn())
        .withTimeStampOut(savedLog.getTimeStampOut())
        .withVehicle(vehicle)
        .withParkingId(parkingEntity.getId())
        .withSlotNumber(savedLog.getParkingSlotEntity().getSlotNumber())
        .withAmount(amount)
        .build();
  }

  private ParkingLogEntity checkOut(long orderId)
      throws OrderNotFoundException, CheckOutAlreadyPerformedException {
    Optional<ParkingLogEntity> optionalLog = parkingLogsDao.findById(orderId);

    ParkingLogEntity log =
        optionalLog.orElseThrow(
            () ->
                new OrderNotFoundException(String.format(ORDER_NOT_FOUND_ERROR_MESSAGE, orderId)));

    if (Objects.nonNull(log.getTimeStampOut())) {
      LOG.error(CHECKOUT_ALREADY_PERFOMED_ERROR_MESSAGE);
      throw new CheckOutAlreadyPerformedException(CHECKOUT_ALREADY_PERFOMED_ERROR_MESSAGE);
    }

    log.setTimeStampOut(ZonedDateTime.now());

    return parkingLogsDao.save(log);
  }

  Price computeAmount(ParkingLogEntity savedLog)
      throws NoPricingPolicyFound, TemporaryDataInconsistencyException {
    ParkingEntity parkingEntity = savedLog.getParkingSlotEntity().getParkingEntity();
    PricingPolicy pricingPolicy =
        PricingPolicyFactory.createPricingPolicy(parkingEntity)
            .orElseThrow(
                () ->
                    new NoPricingPolicyFound(
                        String.format(NO_POLICY_FOUND_ERROR_MESSAGE, parkingEntity.getId())));

    ZonedDateTime timeStampIn = savedLog.getTimeStampIn();
    ZonedDateTime timeStampOut = savedLog.getTimeStampOut();

    return pricingPolicy.calculateAmount(timeStampIn, timeStampOut);
  }
}
