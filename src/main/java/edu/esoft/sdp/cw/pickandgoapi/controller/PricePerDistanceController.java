package edu.esoft.sdp.cw.pickandgoapi.controller;

import edu.esoft.sdp.cw.pickandgoapi.dto.PricePerDistanceDTO;
import edu.esoft.sdp.cw.pickandgoapi.dto.PricePerWeightDTO;
import edu.esoft.sdp.cw.pickandgoapi.service.PricePerDistanceService;
import edu.esoft.sdp.cw.pickandgoapi.service.PricePerWeightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/price-per-distance")
@RequiredArgsConstructor
public class PricePerDistanceController {

    private final PricePerDistanceService pricePerDistanceService;

    @PostMapping
    public ResponseEntity<PricePerDistanceDTO> saveOrUpdate(@RequestBody PricePerDistanceDTO pricePerDistanceDTO) throws Exception {

        return ResponseEntity.ok(pricePerDistanceService.save(pricePerDistanceDTO));

    }

    @GetMapping(value = "/find-by-id/{id}")
    public ResponseEntity<PricePerDistanceDTO> findById(@PathVariable Long id) throws Exception {

        return ResponseEntity.ok(pricePerDistanceService.findById(id));

    }

    @GetMapping
    public ResponseEntity<List<PricePerDistanceDTO>> findAll() throws Exception {

        return ResponseEntity.ok(pricePerDistanceService.findAll());

    }
}
