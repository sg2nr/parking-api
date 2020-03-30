package com.parking.parkingapi.entities.converter;

import com.parking.parkingapi.entities.VehiclePoweredType;
import org.apache.logging.log4j.util.Strings;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class VehiclePoweredTypeConverter implements AttributeConverter<VehiclePoweredType, String> {

  @Override
  public String convertToDatabaseColumn(VehiclePoweredType vehiclePoweredType) {
    if (vehiclePoweredType == null) {
      return null;
    }
    return vehiclePoweredType.getType();
  }

  @Override
  public VehiclePoweredType convertToEntityAttribute(String type) {
    if (Strings.isBlank(type)) {
      return null;
    }

    return Stream.of(VehiclePoweredType.values())
        .filter(t -> type.equals(t.getType()))
        .findFirst()
        .orElseThrow(IllegalArgumentException::new);
  }
}
