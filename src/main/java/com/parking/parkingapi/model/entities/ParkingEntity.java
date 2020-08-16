package com.parking.parkingapi.model.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "parkings")
@Getter
@Setter
public class ParkingEntity {

  @Id
  @GeneratedValue(strategy=IDENTITY)
  private long id;

  @Column(nullable = false, name = "parking_name")
  private String name;

  @Column(nullable = false)
  private String address;

  @Column(nullable = false)
  private String city;

  @ManyToOne
  @JoinColumn(name = "pricing_policy")
  private PricingPolicyEntity pricingPolicyEntity;
}
