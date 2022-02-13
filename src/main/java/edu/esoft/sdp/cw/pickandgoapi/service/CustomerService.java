package edu.esoft.sdp.cw.pickandgoapi.service;

import java.util.List;

import edu.esoft.sdp.cw.pickandgoapi.dto.CustomerDTO;
import edu.esoft.sdp.cw.pickandgoapi.entity.Customer;

public interface CustomerService {

  boolean userNameExists(String userName) throws Exception;

  CustomerDTO save(CustomerDTO customerDTO) throws Exception;

  CustomerDTO findByUserName(String userName) throws Exception;

  List<CustomerDTO> findAll() throws Exception;

  CustomerDTO convertCustomerToCustomerDTO(Customer customer);
}
