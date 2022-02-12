package edu.esoft.sdp.cw.pickandgoapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.esoft.sdp.cw.pickandgoapi.dto.CenterDTO;
import edu.esoft.sdp.cw.pickandgoapi.service.CenterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping(value = "/centers")
@RequiredArgsConstructor
public class CenterController {

  private final CenterService centerService;

  @PostMapping
  public ResponseEntity<CenterDTO> saveOrUpdate(@RequestBody final CenterDTO centerDTO) {
    try {
      return ResponseEntity.ok(centerService.saveOrUpdate(centerDTO));
    } catch (Exception e) {
      log.error(e.getMessage());
      return null;
    }
  }

  @GetMapping(value = "/{status}")
  public ResponseEntity<List<CenterDTO>> getAllActiveCenters(@PathVariable String status) {
    try {
      return ResponseEntity.ok(
          "ACTIVE".equalsIgnoreCase(status)
              ? centerService.getAllActiveCenters()
              : centerService.getAllInActiveCenters());
    } catch (Exception e) {
      log.error(e.getMessage());
      return null;
    }
  }

  @GetMapping(value = "/{registrationId}")
  public ResponseEntity<CenterDTO> getCenterById(
      @PathVariable("registrationId") final Long registrationNo) {
    try {
      return ResponseEntity.ok(centerService.getCenterById(registrationNo));
    } catch (Exception e) {
      log.error(e.getMessage());
      return null;
    }
  }

  @DeleteMapping(value = "/{registrationId}")
  public ResponseEntity<CenterDTO> deleteById(
      @PathVariable("registrationId") final Long registrationNo) {
    try {
      return ResponseEntity.ok(centerService.deleteCenter(registrationNo));
    } catch (Exception e) {
      log.error(e.getMessage());
      return null;
    }
  }
}
