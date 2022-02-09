package edu.esoft.sdp.cw.pickandgoapi.controller;

import edu.esoft.sdp.cw.pickandgoapi.dto.CenterDTO;
import edu.esoft.sdp.cw.pickandgoapi.service.CenterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

  @GetMapping(value = "/all-active")
  public ResponseEntity<List<CenterDTO>> getAllActiveCenters() {
    try {
      return ResponseEntity.ok(centerService.getAllActiveCenters());
    } catch (Exception e) {
      log.error(e.getMessage());
      return null;
    }
  }

  @GetMapping(value = "/all-inactive")
  public ResponseEntity<List<CenterDTO>> getAllInActiveCenters() {
    try {
      return ResponseEntity.ok(centerService.getAllInActiveCenters());
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
