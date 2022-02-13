package edu.esoft.sdp.cw.pickandgoapi.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.esoft.sdp.cw.pickandgoapi.dto.DeliveryRequestDTO;
import edu.esoft.sdp.cw.pickandgoapi.dto.DeliveryResponseDTO;
import edu.esoft.sdp.cw.pickandgoapi.service.DeliveryRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping(value = "/delivery-requests")
@RequiredArgsConstructor
public class DeliveryRequestController {

  private final DeliveryRequestService deliveryRequestService;

  @PostMapping
  public ResponseEntity<DeliveryResponseDTO> createDeliveryRequest(
      @RequestBody @Valid final DeliveryRequestDTO deliveryRequest) {

    return ResponseEntity.ok(deliveryRequestService.createDeliveryRequest(deliveryRequest));
  }

  @GetMapping("/find-by-internal-id/{internalId}")
  public ResponseEntity<DeliveryResponseDTO> getDeliveryRequestByInternalId(
      @PathVariable final String internalId) {
    return ResponseEntity.ok(deliveryRequestService.getDeliveryRequestByInternalId(internalId));
  }
}
