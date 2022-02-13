package edu.esoft.sdp.cw.pickandgoapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.esoft.sdp.cw.pickandgoapi.entity.Customer;
import edu.esoft.sdp.cw.pickandgoapi.entity.DeliveryRequest;
import edu.esoft.sdp.cw.pickandgoapi.enums.DeliveryRequestStatus;

public interface DeliveryRequestRepository extends JpaRepository<DeliveryRequest, Long> {

  Optional<DeliveryRequest> findDeliveryRequestByInternalId(String internalId);

  List<DeliveryRequest> findAllByStatus(DeliveryRequestStatus status);

  List<DeliveryRequest> findAllByCustomer(Customer customer);

  List<DeliveryRequest> findAllByCustomerAndStatus(Customer customer, DeliveryRequestStatus status);
}
