package edu.esoft.sdp.cw.pickandgoapi.service.impl;

import edu.esoft.sdp.cw.pickandgoapi.dto.CustomerDTO;
import edu.esoft.sdp.cw.pickandgoapi.entity.Customer;
import edu.esoft.sdp.cw.pickandgoapi.exception.NotFoundException;
import edu.esoft.sdp.cw.pickandgoapi.repository.CustomerRepository;
import edu.esoft.sdp.cw.pickandgoapi.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public boolean userNameExists(String userName) throws Exception {

        return customerRepository.findById(userName).isPresent();
    }

    @Override
    public CustomerDTO save(CustomerDTO customerDTO) throws Exception {

        Customer customer = convertCustomerDtoToCustomer(customerDTO);

        Optional<Customer> optionalCustomer = customerRepository.findById(customerDTO.getUserName());

        optionalCustomer.ifPresent(value -> customer.setUserName(value.getUserName()));

        return convertCustomerToCustomerDTO(customerRepository.save(customer));
    }

    @Override
    public CustomerDTO findByUserName(String userName) throws Exception {

        return convertCustomerToCustomerDTO(
                customerRepository
                        .findById(userName)
                        .orElseThrow(
                                () ->
                                        new NotFoundException(
                                                "Customer not found for userName: " + userName)));
    }

    @Override
    public List<CustomerDTO> findAll() throws Exception {
        return customerRepository.findAll().stream()
                .map(this::convertCustomerToCustomerDTO)
                .collect(Collectors.toList());
    }

    private CustomerDTO convertCustomerToCustomerDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);

        return customerDTO;
    }

    private Customer convertCustomerDtoToCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);

        return customer;
    }
}
