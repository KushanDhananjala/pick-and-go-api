package edu.esoft.sdp.cw.pickandgoapi.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import edu.esoft.sdp.cw.pickandgoapi.dto.UserDTO;
import edu.esoft.sdp.cw.pickandgoapi.entity.User;

public interface UserDetailsService extends org.springframework.security.core.userdetails.UserDetailsService {

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

}
