package edu.esoft.sdp.cw.pickandgoapi.controller;

import edu.esoft.sdp.cw.pickandgoapi.dto.CustomerDTO;
import edu.esoft.sdp.cw.pickandgoapi.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping(value = "/user-name-exists/{userName}")
    public ResponseEntity<Boolean> userNameExists(@PathVariable String userName) throws Exception {

        return ResponseEntity.ok(customerService.userNameExists(userName));

    }

    @PostMapping
    public ResponseEntity<CustomerDTO> saveOrUpdate(@RequestBody CustomerDTO customerDTO) throws Exception {

        return ResponseEntity.ok(customerService.save(customerDTO));

    }

    @GetMapping(value = "/find-by-user-name/{userName}")
    public ResponseEntity<CustomerDTO> findByUserName(@PathVariable String userName) throws Exception {

        return ResponseEntity.ok(customerService.findByUserName(userName));

    }

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> findAll() throws Exception {

        return ResponseEntity.ok(customerService.findAll());

    }
}
