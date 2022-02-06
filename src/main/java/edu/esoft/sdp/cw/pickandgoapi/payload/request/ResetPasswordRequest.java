package edu.esoft.sdp.cw.pickandgoapi.payload.request;

import lombok.Data;

@Data
public class ResetPasswordRequest {

    private String email;
    private String password;
    private String tempPassword;

}
