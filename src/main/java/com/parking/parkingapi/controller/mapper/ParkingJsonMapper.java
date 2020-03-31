package com.parking.parkingapi.controller.mapper;

import com.parking.parkingapi.model.common.ParkingServiceType;
import com.parking.parkingapi.model.parking.Parking;
import com.parking.parkingapi.model.parking.ParkingBuilder;
import com.parking.parkingapi.model.parking.request.CreateParkingRequest;
import com.parking.parkingapi.model.parking.response.DisplayParkingResponse;
import com.parking.parkingapi.model.parking.response.DisplayParkingResponseBuilder;
import com.parking.parkingapi.model.parking.response.ParkingStatistics;
import com.parking.parkingapi.model.parking.response.ParkingStatus;
import com.parking.parkingapi.model.parkingslot.ParkingSlot;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

/**
 * This class is intended for the mapping JSON - DTO and vice-versa of Parking
 * objects.
 */
@Component
public class ParkingJsonMapper {

  public DisplayParkingResponse mapToResponse(Parking parking) {

    return DisplayParkingResponseBuilder.builder()
        .withId(parking.getId())
        .withName(parking.getName())
        .withAddress(parking.getAddress())
        .withCity(parking.getCity())
        .withStatistics(mapParkingStatistics(parking.getParkingSlots()))
        .build();
  }

  public Parking mapToDto(CreateParkingRequest request) {

    List<ParkingSlot> parkingSlots = request.getRequestedSlots().entrySet()
        .stream()
        .map(r -> createParkingSlots(r.getKey(), r.getValue()))
        .flatMap(Collection::stream)
        .collect(Collectors.toList());

    return ParkingBuilder.builder()
        .withName(request.getName())
        .withAddress(request.getAddress())
        .withCity(request.getCity())
        .withParkingSlots(parkingSlots)
        .build();
  }

  private ParkingStatistics mapParkingStatistics(List<ParkingSlot> parkingSlots) {
    ParkingStatus overallStatus = new ParkingStatus(parkingSlots.size(),
        parkingSlots.stream().filter(ParkingSlot::isFree).count());

    Map<ParkingServiceType, Long> totalSlotsPerTypeMap = parkingSlots.stream()
        .map(ParkingSlot::getOfferedService)
        .collect(groupingBy(Function.identity(), Collectors.counting()));

    Map<ParkingServiceType, Long> freeSlotsPerTypeMap = parkingSlots.stream()
        .filter(ParkingSlot::isFree)
        .map(ParkingSlot::getOfferedService)
        .collect(groupingBy(Function.identity(), Collectors.counting()));

    Map<ParkingServiceType, ParkingStatus> statusPerServiceType = Stream.of(ParkingServiceType.values())
        .collect(toMap(t -> t, t -> new ParkingStatus(totalSlotsPerTypeMap.getOrDefault(t, 0L),
            freeSlotsPerTypeMap.getOrDefault(t, 0L))));

    return new ParkingStatistics(overallStatus, statusPerServiceType);
  }

  private List<ParkingSlot> createParkingSlots(ParkingServiceType type, int size) {
    AtomicInteger slotNumber = new AtomicInteger(1);
    return Stream.generate(ParkingSlot::new)
        .limit(size)
        .map(s -> {
          ParkingSlot ps = new ParkingSlot();
          ps.setOfferedService(type);
          ps.setNumber(slotNumber.getAndIncrement());
          return ps;
        })
        .collect(Collectors.toList());
  }
}
