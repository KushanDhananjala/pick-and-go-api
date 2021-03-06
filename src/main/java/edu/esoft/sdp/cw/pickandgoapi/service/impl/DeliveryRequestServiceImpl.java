package edu.esoft.sdp.cw.pickandgoapi.service.impl;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import edu.esoft.sdp.cw.pickandgoapi.dto.DeliveryRequestDTO;
import edu.esoft.sdp.cw.pickandgoapi.dto.DeliveryResponseDTO;
import edu.esoft.sdp.cw.pickandgoapi.entity.Customer;
import edu.esoft.sdp.cw.pickandgoapi.entity.DeliveryRequest;
import edu.esoft.sdp.cw.pickandgoapi.entity.Package;
import edu.esoft.sdp.cw.pickandgoapi.entity.User;
import edu.esoft.sdp.cw.pickandgoapi.enums.DeliveryRequestStatus;
import edu.esoft.sdp.cw.pickandgoapi.enums.ERole;
import edu.esoft.sdp.cw.pickandgoapi.exception.NotFoundException;
import edu.esoft.sdp.cw.pickandgoapi.exception.PickAndGoBadRequest;
import edu.esoft.sdp.cw.pickandgoapi.facade.InternalIdGenerator;
import edu.esoft.sdp.cw.pickandgoapi.repository.CustomerRepository;
import edu.esoft.sdp.cw.pickandgoapi.repository.DeliveryRequestRepository;
import edu.esoft.sdp.cw.pickandgoapi.repository.PackageRepository;
import edu.esoft.sdp.cw.pickandgoapi.repository.UserRepository;
import edu.esoft.sdp.cw.pickandgoapi.service.CustomerService;
import edu.esoft.sdp.cw.pickandgoapi.service.DeliveryRequestService;
import edu.esoft.sdp.cw.pickandgoapi.service.PackageService;
import edu.esoft.sdp.cw.pickandgoapi.service.UserRegisterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeliveryRequestServiceImpl implements DeliveryRequestService {

  private final DeliveryRequestRepository deliveryRequestRepository;
  private final CustomerService customerService;
  private final PackageService packageService;
  private final CustomerRepository customerRepository;
  private final UserRepository userRepository;
  private final PackageRepository packageRepository;
  private final UserRegisterService userRegisterService;
  private final InternalIdGenerator internalIdGenerator;

  @Override
  public DeliveryResponseDTO createDeliveryRequest(final DeliveryRequestDTO deliveryRequest) {
    final Customer customer = getCustomer(deliveryRequest.getCustomerUserName());

    final Package aPackage =
        packageRepository
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
    request.setAPackage(aPackage);
    request.setInternalId(internalIdGenerator.getId());
    request.setStatus(DeliveryRequestStatus.PENDING);
    final DeliveryRequest saveRequest = deliveryRequestRepository.save(request);

    return convertDeliveryRequestTODeliveryResponseDTO(saveRequest);
  }

  @Override
  public DeliveryResponseDTO getDeliveryRequestByInternalId(final String internalId) {
    final DeliveryRequest deliveryRequest = getDeliveryRequest(internalId);
    return convertDeliveryRequestTODeliveryResponseDTO(deliveryRequest);
  }

  @Override
  public void assignRider(final String deliveryRequestInternalId, final String riderUserName) {
    final DeliveryRequest deliveryRequest = getDeliveryRequest(deliveryRequestInternalId);

    final User rider =
        userRepository
            .findByUsername(riderUserName)
            .orElseThrow(
                () ->
                    new UsernameNotFoundException(
                        "User Not Found with username: " + riderUserName));
    if (!CollectionUtils.isEmpty(rider.getRoles())
        && rider.getRoles().stream().anyMatch(role -> role.getName().equals(ERole.ROLE_RIDER))) {
      deliveryRequest.setRider(rider);
      deliveryRequest.setStatus(DeliveryRequestStatus.DRIVER_ASSIGN);
      deliveryRequest.setAcceptedTime(Instant.now());
      deliveryRequestRepository.save(deliveryRequest);
    } else {
      throw new PickAndGoBadRequest(
          "Unable to find User With Rider Role for userName :" + riderUserName);
    }
  }

  @Override
  public void updateStatus(
      final String deliveryRequestInternalId, final DeliveryRequestStatus status) {
    final DeliveryRequest deliveryRequest = getDeliveryRequest(deliveryRequestInternalId);

    deliveryRequest.setStatus(status);
    deliveryRequestRepository.save(deliveryRequest);
  }

  @Override
  public List<DeliveryResponseDTO> getRequestsByStatus(final String status) {
    return deliveryRequestRepository.findAllByStatus(DeliveryRequestStatus.valueOf(status)).stream()
        .map(this::convertDeliveryRequestTODeliveryResponseDTO)
        .collect(Collectors.toList());
  }

  @Override
  public List<DeliveryResponseDTO> getRequestsByCustomerAndFilterByStatus(
      final String customerUserName, final String status) {
    final Customer customer = getCustomer(customerUserName);
    if (StringUtils.hasText(status)) {
      return deliveryRequestRepository
          .findAllByCustomerAndStatus(customer, DeliveryRequestStatus.valueOf(status))
          .stream()
          .map(this::convertDeliveryRequestTODeliveryResponseDTO)
          .collect(Collectors.toList());
    }
    return deliveryRequestRepository.findAllByCustomer(customer).stream()
        .map(this::convertDeliveryRequestTODeliveryResponseDTO)
        .collect(Collectors.toList());
  }

  @Override
  public List<DeliveryResponseDTO> getAllDeliveryRequests() {
    return deliveryRequestRepository.findAll().stream()
        .map(this::convertDeliveryRequestTODeliveryResponseDTO)
        .collect(Collectors.toList());
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

    deliveryResponseDTO.setItem(
        packageService.convertPackageToPackageDTO(deliveryRequest.getAPackage()));
    deliveryResponseDTO.setCustomer(
        customerService.convertCustomerToCustomerDTO(deliveryRequest.getCustomer()));

    if (deliveryRequest.getUser() != null) {
      deliveryResponseDTO.setUserName(deliveryRequest.getUser().getUsername());
    }
    if (deliveryRequest.getRider() != null) {
      deliveryResponseDTO.setRider(
          userRegisterService.convertUserToUserDTO(deliveryRequest.getRider()));
    }
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

  private Customer getCustomer(final String customer) {
    return customerRepository
        .findById(customer)
        .orElseThrow(() -> new NotFoundException("Customer not found for userName: " + customer));
  }
}
