package edu.esoft.sdp.cw.pickandgoapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.esoft.sdp.cw.pickandgoapi.entity.DeliveryRequest;

public interface DeliveryRequestRepository extends JpaRepository<DeliveryRequest, Long> {

  Optional<DeliveryRequest> findDeliveryRequestByInternalId(String internalId);
}
