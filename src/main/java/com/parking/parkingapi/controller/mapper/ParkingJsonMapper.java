package com.parking.parkingapi.controller.mapper;

import com.parking.parkingapi.exception.InvalidInputDataException;
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
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

/**
 * This class is intended for the mapping JSON - Domain model, and vice-versa, of Parking
 * objects.
 */
@Component
public class ParkingJsonMapper {

  private static final String PARKING_GENERAL_INFORMATION_MANDATORY =
      "Parking general information, such as 'name', 'address' and 'city', are mandatory";

  private static final String ELEMENT_REQUESTED_SLOTS_IS_MANDATORY = "Element 'requestedSlots' is mandatory";

  private static final String ELEMENT_PRICING_POLICY_ID_IS_MANDATORY = "Element 'pricingPolicyId' is mandatory";

  public DisplayParkingResponse mapToResponse(@NotNull Parking parking) {

    return DisplayParkingResponseBuilder.builder()
        .withId(parking.getId())
        .withName(parking.getName())
        .withAddress(parking.getAddress())
        .withCity(parking.getCity())
        .withStatistics(mapParkingStatistics(parking.getParkingSlots()))
        .withPricingPolicy(parking.getPricingPolicy())
        .build();
  }

  public Parking mapToParking(@NotNull CreateParkingRequest request) throws InvalidInputDataException {

    // Perform input validation
    if (Objects.isNull(request.getName()) || Objects.isNull(request.getAddress()) || Objects.isNull(request.getCity())) {
      throw new InvalidInputDataException(PARKING_GENERAL_INFORMATION_MANDATORY);
    }

    Map<ParkingServiceType, Integer> requestedSlots = request.getRequestedSlots();
    if (CollectionUtils.isEmpty(requestedSlots)) {
      throw new InvalidInputDataException(ELEMENT_REQUESTED_SLOTS_IS_MANDATORY);
    }

    if (Objects.isNull(request.getPricingPolicyId())) {
      throw new InvalidInputDataException(ELEMENT_PRICING_POLICY_ID_IS_MANDATORY);
    }

    List<ParkingSlot> parkingSlots = createParkingSlots(requestedSlots);

    return ParkingBuilder.builder()
        .withName(request.getName())
        .withAddress(request.getAddress())
        .withCity(request.getCity())
        .withParkingSlots(parkingSlots)
        .withPricingPolicyRequestId(request.getPricingPolicyId())
        .build();
  }

  private List<ParkingSlot> createParkingSlots(Map<ParkingServiceType, Integer> requestedSlots) {
    AtomicInteger slotNumber = new AtomicInteger(1);
    return requestedSlots.entrySet()
        .stream()
        .map(r -> createParkingSlots(slotNumber, r.getKey(), r.getValue()))
        .flatMap(Collection::stream)
        .collect(Collectors.toList());
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

  private List<ParkingSlot> createParkingSlots(AtomicInteger slotNumber, ParkingServiceType type, int size) {
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
