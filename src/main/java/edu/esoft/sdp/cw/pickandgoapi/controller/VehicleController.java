package edu.esoft.sdp.cw.pickandgoapi.controller;

import edu.esoft.sdp.cw.pickandgoapi.dto.VehicleDTO;
import edu.esoft.sdp.cw.pickandgoapi.service.VehicleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping(value = "/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;

    @PostMapping
    public ResponseEntity<VehicleDTO> saveOrUpdate(@Valid @RequestBody final VehicleDTO vehicleDTO) {
        return ResponseEntity.ok(vehicleService.saveOrUpdate(vehicleDTO));
    }

    @GetMapping(value = "/by-availability/{availability}")
    public ResponseEntity<List<VehicleDTO>> getAllVehicleByAvailability(@PathVariable boolean availability) {
        return ResponseEntity.ok(vehicleService.getAllByStatus(availability));
    }

    @GetMapping(value = "/{vehicleNo}")
    public ResponseEntity<VehicleDTO> getVehicleByVehicleNo(
            @PathVariable("vehicleNo") final String vehicleNo) {
        try {
            return ResponseEntity.ok(vehicleService.getVehicleByVehicleNo(vehicleNo));
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    @PutMapping(value = "/update-availability/{vehicleNo}{availability}")
    public ResponseEntity<VehicleDTO> updateAvailability(@PathVariable String vehicleNo, @PathVariable boolean availability) {
        return ResponseEntity.ok(vehicleService.updateAvailability(vehicleNo, availability));
    }
}
