package com.parking.parkingapi.model.entities.converter;

import com.parking.parkingapi.model.entities.EngineType;
import org.apache.logging.log4j.util.Strings;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class VehiclePoweredTypeConverter implements AttributeConverter<EngineType, String> {

  @Override
  public String convertToDatabaseColumn(EngineType engineType) {
    if (engineType == null) {
      return null;
    }
    return engineType.getType();
  }

  @Override
  public EngineType convertToEntityAttribute(String type) {
    if (Strings.isBlank(type)) {
      return null;
    }

    return Stream.of(EngineType.values())
        .filter(t -> type.equals(t.getType()))
        .findFirst()
        .orElseThrow(IllegalArgumentException::new);
  }
}
