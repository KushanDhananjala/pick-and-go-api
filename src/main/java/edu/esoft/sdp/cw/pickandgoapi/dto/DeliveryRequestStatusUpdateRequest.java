package edu.esoft.sdp.cw.pickandgoapi.dto;

import edu.esoft.sdp.cw.pickandgoapi.enums.DeliveryRequestStatus;
import lombok.Data;

@Data
public class DeliveryRequestStatusUpdateRequest {

  private DeliveryRequestStatus status;
}
