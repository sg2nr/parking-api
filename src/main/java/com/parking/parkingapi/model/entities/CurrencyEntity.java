package com.parking.parkingapi.model.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "currencies")
@Getter
@Setter
public class CurrencyEntity {

  @Id
  @Column(name = "code")
  private String code;

  @Column(name = "decimal_places")
  private int decimalPlaces;

  public CurrencyEntity() {
  }

  public CurrencyEntity(String code, int decimalPlaces) {
    this.code = code;
    this.decimalPlaces = decimalPlaces;
  }
}
