package edu.esoft.sdp.cw.pickandgoapi.service;

import edu.esoft.sdp.cw.pickandgoapi.payload.request.ResetPasswordRequest;
import edu.esoft.sdp.cw.pickandgoapi.payload.request.SignupRequest;
import org.springframework.http.ResponseEntity;

public interface UserRegisterService {

    ResponseEntity<?> createUser(SignupRequest signupRequest);

    ResponseEntity<?> centerUserPasswordChange(ResetPasswordRequest resetPasswordRequest);

    ResponseEntity<?> forgotPassword(String email);

    ResponseEntity<?> resetPassword(ResetPasswordRequest resetPasswordRequest);

    ResponseEntity<?> getAllUsers();

}
