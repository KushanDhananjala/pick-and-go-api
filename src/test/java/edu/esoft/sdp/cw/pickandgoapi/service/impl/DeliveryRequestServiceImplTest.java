package edu.esoft.sdp.cw.pickandgoapi.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import edu.esoft.sdp.cw.pickandgoapi.repository.DeliveryRequestRepository;
import edu.esoft.sdp.cw.pickandgoapi.repository.PackageRepository;
import edu.esoft.sdp.cw.pickandgoapi.repository.UserRepository;

class DeliveryRequestServiceImplTest {

  @InjectMocks private DeliveryRequestServiceImpl deliveryRequestService;
  @Mock private DeliveryRequestRepository deliveryRequestRepository;
  @Mock private PackageRepository packageRepository;
  @Mock private UserRepository userRepository;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void createDeliveryRequest() {}

  @Test
  void getDeliveryRequestByInternalId() {}

  @Test
  void assignRider() {}

  @Test
  void updateStatus() {}

  @Test
  void getRequestsByStatus() {}

  @Test
  void getRequestsByCustomerAndFilterByStatus() {}

  @Test
  void getAllDeliveryRequests() {}
}
