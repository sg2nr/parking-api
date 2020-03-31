package com.parking.parkingapi.service.mapper;

import com.parking.parkingapi.model.common.ParkingServiceType;
import com.parking.parkingapi.model.entities.EngineType;
import org.springframework.stereotype.Component;

@Component
public class ParkingServiceTypeConverter {

  public ParkingServiceType convertToParkingService(EngineType engineType) {
    if (engineType == EngineType.ELECTRICAL_TWENTY_KW) {
      return ParkingServiceType.TWENTY_KW_POWER_SUPPLY;
    }
    if (engineType == EngineType.ELECTRICAL_FIFTY_KW) {
      return ParkingServiceType.FIFTY_KW_POWER_SUPPLY;
    }
    return ParkingServiceType.STANDARD_PARKING;
  }

  public EngineType convertToVehiclePoweredType(ParkingServiceType parkingServiceType) {
    if (parkingServiceType == ParkingServiceType.TWENTY_KW_POWER_SUPPLY) {
      return EngineType.ELECTRICAL_TWENTY_KW;
    }
    if (parkingServiceType == ParkingServiceType.FIFTY_KW_POWER_SUPPLY) {
      return EngineType.ELECTRICAL_FIFTY_KW;
    }
    return EngineType.GASOLINE;
  }
}
