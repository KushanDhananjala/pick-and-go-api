package edu.esoft.sdp.cw.pickandgoapi.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import edu.esoft.sdp.cw.pickandgoapi.entity.User;
import edu.esoft.sdp.cw.pickandgoapi.repository.UserRepository;

class UserDetailsServiceImplTest {

  @InjectMocks private UserDetailsServiceImpl userDetailsService;
  @Mock private UserRepository repository;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void loadUserByUsername() {
    User user = new User();
    user.setUsername("Jon");
    user.setId(1L);
    when(repository.findByUsername(any())).thenReturn(Optional.of(user));

    UserDetails userDetails = userDetailsService.loadUserByUsername("Jon");
    Assertions.assertNotNull(userDetails);
  }

  @Test
  void loadUserByUsername_exception() {
    when(repository.findByUsername(any())).thenReturn(Optional.empty());

    Assertions.assertThrows(
        UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername("Jon"));
  }
}
