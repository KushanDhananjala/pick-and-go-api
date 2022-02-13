package edu.esoft.sdp.cw.pickandgoapi.service.impl;

import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import edu.esoft.sdp.cw.pickandgoapi.dto.DeliveryRequestDTO;
import edu.esoft.sdp.cw.pickandgoapi.dto.DeliveryResponseDTO;
import edu.esoft.sdp.cw.pickandgoapi.entity.Customer;
import edu.esoft.sdp.cw.pickandgoapi.entity.DeliveryRequest;
import edu.esoft.sdp.cw.pickandgoapi.entity.Item;
import edu.esoft.sdp.cw.pickandgoapi.entity.User;
import edu.esoft.sdp.cw.pickandgoapi.enums.DeliveryRequestStatus;
import edu.esoft.sdp.cw.pickandgoapi.exception.NotFoundException;
import edu.esoft.sdp.cw.pickandgoapi.repository.CustomerRepository;
import edu.esoft.sdp.cw.pickandgoapi.repository.DeliveryRequestRepository;
import edu.esoft.sdp.cw.pickandgoapi.repository.ItemRepository;
import edu.esoft.sdp.cw.pickandgoapi.repository.UserRepository;
import edu.esoft.sdp.cw.pickandgoapi.service.CustomerService;
import edu.esoft.sdp.cw.pickandgoapi.service.DeliveryRequestService;
import edu.esoft.sdp.cw.pickandgoapi.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeliveryRequestServiceImpl implements DeliveryRequestService {

  private final DeliveryRequestRepository deliveryRequestRepository;
  private final CustomerService customerService;
  private final ItemService itemService;
  private final CustomerRepository customerRepository;
  private final UserRepository userRepository;
  private final ItemRepository itemRepository;

  @Override
  public DeliveryResponseDTO createDeliveryRequest(final DeliveryRequestDTO deliveryRequest) {
    final Customer customer =
        customerRepository
            .findById(deliveryRequest.getCustomerUserName())
            .orElseThrow(
                () ->
                    new NotFoundException(
                        "Customer not found for userName: "
                            + deliveryRequest.getCustomerUserName()));

    final Item item =
        itemRepository
            .findById(deliveryRequest.getItemId())
            .orElseThrow(
                () ->
                    new NotFoundException("Item not found for id: " + deliveryRequest.getItemId()));

    final DeliveryRequest request = convertDeliveryRequestDTToDeliveryRequest(deliveryRequest);

    if (StringUtils.hasText(deliveryRequest.getUserName())) {
      final User user =
          userRepository
              .findByUsername(deliveryRequest.getUserName())
              .orElseThrow(
                  () ->
                      new UsernameNotFoundException(
                          "User Not Found with username: " + deliveryRequest.getUserName()));
      request.setUser(user);
    }
    request.setCustomer(customer);
    request.setItem(item);
    request.setInternalId(UUID.randomUUID().toString());
    request.setStatus(DeliveryRequestStatus.PENDING);
    final DeliveryRequest saveRequest = deliveryRequestRepository.save(request);

    final DeliveryResponseDTO response = convertDeliveryRequestTODeliveryResponseDTO(saveRequest);

    if (saveRequest.getUser() != null) {
      response.setUserName(saveRequest.getUser().getUsername());
    }
    response.setItem(itemService.convertItemToItemDTO(item));
    response.setCustomer(customerService.convertCustomerToCustomerDTO(customer));
    return response;
  }

  @Override
  public DeliveryResponseDTO getDeliveryRequestByInternalId(final String internalId) {
    return convertDeliveryRequestTODeliveryResponseDTO(getDeliveryRequest(internalId));
  }

  private DeliveryRequest convertDeliveryRequestDTToDeliveryRequest(
      final DeliveryRequestDTO deliveryRequestDTO) {
    final DeliveryRequest deliveryRequest = new DeliveryRequest();
    BeanUtils.copyProperties(deliveryRequestDTO, deliveryRequest);

    return deliveryRequest;
  }

  private DeliveryResponseDTO convertDeliveryRequestTODeliveryResponseDTO(
      final DeliveryRequest deliveryRequest) {
    final DeliveryResponseDTO deliveryResponseDTO = new DeliveryResponseDTO();
    BeanUtils.copyProperties(deliveryRequest, deliveryResponseDTO);
    return deliveryResponseDTO;
  }

  private DeliveryRequest getDeliveryRequest(final String deliveryRequestInternalId) {
    return deliveryRequestRepository
        .findDeliveryRequestByInternalId(deliveryRequestInternalId)
        .orElseThrow(
            () ->
                new NotFoundException(
                    "Unable to find Delivery Order Request for Internal Id: "
                        + deliveryRequestInternalId));
  }
}
