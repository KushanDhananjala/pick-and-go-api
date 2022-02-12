package edu.esoft.sdp.cw.pickandgoapi.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "centers")
public class Center extends Auditable<Long> implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long registrationId;

  private String name;
  private float latitude;
  private float longitude;
  private int isActive;
}
