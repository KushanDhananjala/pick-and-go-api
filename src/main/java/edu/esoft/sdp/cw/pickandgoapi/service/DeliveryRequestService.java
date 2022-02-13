package edu.esoft.sdp.cw.pickandgoapi.service;

import edu.esoft.sdp.cw.pickandgoapi.dto.DeliveryRequestDTO;
import edu.esoft.sdp.cw.pickandgoapi.dto.DeliveryResponseDTO;

public interface DeliveryRequestService {

  DeliveryResponseDTO createDeliveryRequest(DeliveryRequestDTO deliveryRequest);

  DeliveryResponseDTO getDeliveryRequestByInternalId(String internalId);

  void assignRider(String deliveryRequestInternalId, String riderUserName);
}
