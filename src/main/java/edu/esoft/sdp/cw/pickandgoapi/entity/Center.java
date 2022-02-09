package edu.esoft.sdp.cw.pickandgoapi.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "centers")
public class Center extends Auditable<Long> implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long registrationId;

  private String name;
  private double capacity;
  private float latitude;
  private float longitude;
  private int isActive;
}
