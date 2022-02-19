package edu.esoft.sdp.cw.pickandgoapi.controller;

import edu.esoft.sdp.cw.pickandgoapi.dto.PackageDTO;
import edu.esoft.sdp.cw.pickandgoapi.service.PackageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/packages")
@RequiredArgsConstructor
public class PackageController {

    private final PackageService packageService;

    @PostMapping
    public ResponseEntity<PackageDTO> saveOrUpdate(@RequestBody PackageDTO packageDTO) throws Exception {

        return ResponseEntity.ok(packageService.save(packageDTO));

    }

    @GetMapping(value = "/find-by-id/{packageId}")
    public ResponseEntity<PackageDTO> findPackageById(@PathVariable Long packageId) throws Exception {

        return ResponseEntity.ok(packageService.findItemById(packageId));

    }

    @GetMapping
    public ResponseEntity<List<PackageDTO>> findAll() throws Exception {

        return ResponseEntity.ok(packageService.findAll());

    }
}
