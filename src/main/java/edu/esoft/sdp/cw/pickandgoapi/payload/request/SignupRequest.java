package edu.esoft.sdp.cw.pickandgoapi.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {

    private String username;
    private String email;
    private Set<String> role;
    private String password;
    private int mobileNumber;
    private Long registrationId;

}
