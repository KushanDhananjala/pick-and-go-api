package edu.esoft.sdp.cw.pickandgoapi.dto;

import java.math.BigDecimal;
import java.time.Instant;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class DeliveryRequestDTO {

  @NotEmpty(message = "Order Display id is required")
  private String displayId;

  @NotEmpty(message = "Order Display id is required")
  private String customerUserName;

  private String userName;

  @NotNull(message = "Order Item id is required")
  private Long itemId;

  @NotNull(message = "Order Delivery fee is required")
  private BigDecimal deliveryFee;

  @NotEmpty(message = "From Address  is required")
  private String fromAddress;

  @NotEmpty(message = "To Address is required")
  private String toAddress;

  @NotNull(message = "Order Distance is required")
  private Double distance;

  @NotNull(message = "Order Requested Time is required")
  private Instant requestedTime;
}
