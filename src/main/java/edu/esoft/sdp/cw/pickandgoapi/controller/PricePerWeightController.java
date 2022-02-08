package edu.esoft.sdp.cw.pickandgoapi.controller;

import edu.esoft.sdp.cw.pickandgoapi.dto.PricePerWeightDTO;
import edu.esoft.sdp.cw.pickandgoapi.service.PricePerWeightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/price-per-weight")
@RequiredArgsConstructor
public class PricePerWeightController {

    private final PricePerWeightService pricePerWeightService;

    @PostMapping
    public ResponseEntity<PricePerWeightDTO> saveOrUpdate(@RequestBody PricePerWeightDTO pricePerWeightDTO) throws Exception {

        return ResponseEntity.ok(pricePerWeightService.save(pricePerWeightDTO));

    }

    @GetMapping(value = "/find-by-id/{id}")
    public ResponseEntity<PricePerWeightDTO> findById(@PathVariable Long id) throws Exception {

        return ResponseEntity.ok(pricePerWeightService.findById(id));

    }

    @GetMapping
    public ResponseEntity<List<PricePerWeightDTO>> findAll() throws Exception {

        return ResponseEntity.ok(pricePerWeightService.findAll());

    }
}
