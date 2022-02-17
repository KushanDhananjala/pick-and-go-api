package edu.esoft.sdp.cw.pickandgoapi.controller;

import edu.esoft.sdp.cw.pickandgoapi.dto.MiscellaneousChargesDTO;
import edu.esoft.sdp.cw.pickandgoapi.service.MiscellaneousChargesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/miscellaneous-charges")
@RequiredArgsConstructor
public class MiscellaneousChargesController {

    private final MiscellaneousChargesService miscellaneousChargesService;

    @GetMapping
    public ResponseEntity<List<MiscellaneousChargesDTO>> findAll() throws Exception {

        return ResponseEntity.ok(miscellaneousChargesService.findAll());

    }
}
