package edu.esoft.sdp.cw.pickandgoapi.service;

import java.util.List;

import edu.esoft.sdp.cw.pickandgoapi.dto.DeliveryRequestDTO;
import edu.esoft.sdp.cw.pickandgoapi.dto.DeliveryResponseDTO;
import edu.esoft.sdp.cw.pickandgoapi.enums.DeliveryRequestStatus;

public interface DeliveryRequestService {

  DeliveryResponseDTO createDeliveryRequest(DeliveryRequestDTO deliveryRequest);

  DeliveryResponseDTO getDeliveryRequestByInternalId(String internalId);

  void assignRider(String deliveryRequestInternalId, String riderUserName);

  void updateStatus( String deliveryRequestInternalId, DeliveryRequestStatus status);

  List<DeliveryResponseDTO> getRequestsByStatus(String status);

  List<DeliveryResponseDTO> getRequestsByCustomerAndFilterByStatus(String customer, String status);

  List<DeliveryResponseDTO> getAllDeliveryRequests();
}
