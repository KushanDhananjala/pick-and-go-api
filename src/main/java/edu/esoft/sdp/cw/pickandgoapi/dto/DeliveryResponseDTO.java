package edu.esoft.sdp.cw.pickandgoapi.dto;

import java.math.BigDecimal;
import java.time.Instant;

import edu.esoft.sdp.cw.pickandgoapi.enums.DeliveryRequestStatus;
import lombok.Data;

@Data
public class DeliveryResponseDTO {

  private Long id;
  private String internalId;
  private String userName;
  private String displayId;
  private CustomerDTO customer;
  private ItemDTO item;
  private UserDTO rider;
  private BigDecimal deliveryFee;
  private String fromAddress;
  private String toAddress;
  private double distance;
  private Instant requestedTime;
  private Instant acceptedTime;
  private DeliveryRequestStatus status;
}
