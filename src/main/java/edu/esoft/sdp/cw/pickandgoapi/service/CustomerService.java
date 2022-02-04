package edu.esoft.sdp.cw.pickandgoapi.service;

import edu.esoft.sdp.cw.pickandgoapi.dto.CustomerDTO;

import java.util.List;

public interface CustomerService {

    boolean userNameExists(String userName) throws Exception;

    CustomerDTO save(CustomerDTO customerDTO) throws Exception;

    CustomerDTO findByUserName(String userName) throws Exception;

    List<CustomerDTO> findAll() throws Exception;

}
