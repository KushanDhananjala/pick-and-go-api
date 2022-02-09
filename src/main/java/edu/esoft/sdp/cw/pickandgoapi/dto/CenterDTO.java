package edu.esoft.sdp.cw.pickandgoapi.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class CenterDTO {
  private Long registrationId;
  private String name;
  private double capacity;
  private float latitude;
  private float longitude;
  private int isActive;
  private String createdBy;
  private Instant createdAt;
  private String modifiedBy;
  private Instant modifiedAt;
}
