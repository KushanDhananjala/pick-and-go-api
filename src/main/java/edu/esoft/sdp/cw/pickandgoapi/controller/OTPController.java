package edu.esoft.sdp.cw.pickandgoapi.controller;

import edu.esoft.sdp.cw.pickandgoapi.service.OTPService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/otp-generator")
@RequiredArgsConstructor
public class OTPController {

    private final OTPService otpService;

    @PostMapping(value = "/{mobileNo}")
    public ResponseEntity<Boolean> generateOTP(@PathVariable int mobileNo) throws Exception {

        return ResponseEntity.ok(otpService.generateOTP(mobileNo));

    }

    @PatchMapping(value = "/{mobileNo}")
    public ResponseEntity<Boolean> updateOTP(@PathVariable int mobileNo) throws Exception {

        return ResponseEntity.ok(otpService.updateOTP(mobileNo));

    }

    @GetMapping(value = "/{mobileNo}/{otp}")
    public ResponseEntity<Boolean> validateOTP(@PathVariable int mobileNo, @PathVariable int otp) throws Exception {

        return ResponseEntity.ok(otpService.validateOTP(mobileNo, otp));

    }


}
