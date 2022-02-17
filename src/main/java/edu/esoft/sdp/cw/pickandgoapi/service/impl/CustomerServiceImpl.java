package edu.esoft.sdp.cw.pickandgoapi.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.esoft.sdp.cw.pickandgoapi.dto.CustomerDTO;
import edu.esoft.sdp.cw.pickandgoapi.entity.Customer;
import edu.esoft.sdp.cw.pickandgoapi.exception.NotFoundException;
import edu.esoft.sdp.cw.pickandgoapi.repository.CustomerRepository;
import edu.esoft.sdp.cw.pickandgoapi.service.CustomerService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

  @Override
  public boolean userNameExists(final String userName) throws Exception {

        return customerRepository.findById(userName).isPresent();
    }

  @Override
  public CustomerDTO save(final CustomerDTO customerDTO) throws Exception {

    final Customer customer = convertCustomerDtoToCustomer(customerDTO);

    final Optional<Customer> optionalCustomer =
        customerRepository.findById(customerDTO.getUserName());

        optionalCustomer.ifPresent(value -> customer.setUserName(value.getUserName()));

        return convertCustomerToCustomerDTO(customerRepository.save(customer));
    }

  @Override
  public CustomerDTO findByUserName(final String userName) {

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

  @Override
  public CustomerDTO convertCustomerToCustomerDTO(final Customer customer) {
    final CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);

        return customerDTO;
    }

  private Customer convertCustomerDtoToCustomer(final CustomerDTO customerDTO) {
    final Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);

        return customer;
    }
}
