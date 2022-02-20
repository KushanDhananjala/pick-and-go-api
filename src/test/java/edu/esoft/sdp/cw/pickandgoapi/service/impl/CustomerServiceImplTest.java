package edu.esoft.sdp.cw.pickandgoapi.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import edu.esoft.sdp.cw.pickandgoapi.dto.CustomerDTO;
import edu.esoft.sdp.cw.pickandgoapi.entity.Customer;
import edu.esoft.sdp.cw.pickandgoapi.exception.NotFoundException;
import edu.esoft.sdp.cw.pickandgoapi.repository.CustomerRepository;

class CustomerServiceImplTest {

  @InjectMocks private CustomerServiceImpl customerService;
  @Mock private CustomerRepository customerRepository;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void userNameExists() throws Exception {
    Customer customer = new Customer();
    customer.setUserName("Jon");
    customer.setCustomerName("Jon Doe");
    when(customerRepository.findById(any())).thenReturn(Optional.of(customer));

    boolean userNameExists = customerService.userNameExists("Jon");
    Assertions.assertTrue(userNameExists);
  }

  @Test
  void save() throws Exception {

    Customer customer = new Customer();
    customer.setUserName("Jon");
    customer.setCustomerName("Jon Doe");
    customer.setEmail("jon@doe.com");
    customer.setMobileNo(16295726);
    customer.setAddress("UNKNOWN");
    customer.setGender("Male");
    customer.setAge(25);
    CustomerDTO customerDTO = new CustomerDTO();
    BeanUtils.copyProperties(customer, customerDTO);
    when(customerRepository.findById(any())).thenReturn(Optional.empty());
    when(customerRepository.save(any())).thenReturn(customer);
    CustomerDTO save = customerService.save(customerDTO);
    Assertions.assertNotNull(save);
    Assertions.assertEquals(save.getUserName(), customerDTO.getUserName());
  }

  @Test
  void update() throws Exception {

    Customer customer = new Customer();
    customer.setUserName("Jon");
    customer.setCustomerName("Jon Doe");
    customer.setEmail("jon@doe.com");
    customer.setMobileNo(16295726);
    customer.setAddress("UNKNOWN");
    customer.setGender("Male");
    customer.setAge(25);
    CustomerDTO customerDTO = new CustomerDTO();
    BeanUtils.copyProperties(customer, customerDTO);
    when(customerRepository.findById(any())).thenReturn(Optional.of(customer));
    when(customerRepository.save(any())).thenReturn(customer);
    CustomerDTO save = customerService.save(customerDTO);
    Assertions.assertNotNull(save);
    Assertions.assertEquals(save.getUserName(), customerDTO.getUserName());
  }

  @Test
  void findByUserName() {
    Customer customer = new Customer();
    customer.setUserName("Jon");
    customer.setCustomerName("Jon Doe");
    customer.setEmail("jon@doe.com");
    customer.setMobileNo(16295726);
    customer.setAddress("UNKNOWN");
    customer.setGender("Male");
    customer.setAge(25);
    when(customerRepository.findById(any())).thenReturn(Optional.of(customer));

    CustomerDTO customerDT = customerService.findByUserName("Jon");
    Assertions.assertNotNull(customerDT);
    Assertions.assertEquals(customer.getUserName(), customerDT.getUserName());
  }

  @Test
  void findByUserName_exception() {

    when(customerRepository.findById(any())).thenReturn(Optional.empty());

    Assertions.assertThrows(NotFoundException.class, () -> customerService.findByUserName("Jon"));
  }

  @Test
  void findAll() throws Exception {
    Customer customer = new Customer();
    customer.setUserName("Jon");
    customer.setCustomerName("Jon Doe");
    customer.setEmail("jon@doe.com");
    customer.setMobileNo(16295726);
    customer.setAddress("UNKNOWN");
    customer.setGender("Male");
    customer.setAge(25);
    when(customerRepository.findAll()).thenReturn(List.of(customer));

    List<CustomerDTO> customerDTOS = customerService.findAll();
    Assertions.assertFalse(CollectionUtils.isEmpty(customerDTOS));
    Assertions.assertEquals(1, customerDTOS.size());
  }

  @Test
  void convertCustomerToCustomerDTO() {
    Customer customer = new Customer();
    customer.setUserName("Jon");
    customer.setCustomerName("Jon Doe");
    customer.setEmail("jon@doe.com");
    customer.setMobileNo(16295726);
    customer.setAddress("UNKNOWN");
    customer.setGender("Male");
    customer.setAge(25);

    CustomerDTO customerDTO = customerService.convertCustomerToCustomerDTO(customer);
    Assertions.assertNotNull(customerDTO);
    Assertions.assertEquals(customer.getUserName(), customerDTO.getUserName());
  }
}
