package edu.esoft.sdp.cw.pickandgoapi.controller;

import edu.esoft.sdp.cw.pickandgoapi.service.PriceCalculateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/price-calculator")
@RequiredArgsConstructor
public class PriceCalculateController {

    private final PriceCalculateService priceCalculateService;

    @GetMapping(value = "/delivery-cost/{distance}/{weight}/{miscellaneousTypeId}")
    public ResponseEntity<BigDecimal> calculateDeliveryCharge(@PathVariable double distance, @PathVariable double weight,
                                                              @PathVariable Long miscellaneousTypeId) throws Exception {

        return ResponseEntity.ok(priceCalculateService.calculateDeliveryCharge(distance, weight, miscellaneousTypeId));

    }

}
