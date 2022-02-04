package edu.esoft.sdp.cw.pickandgoapi.service;

public interface OTPService {

    boolean generateOTP(int mobileNo) throws Exception;

    boolean updateOTP(int mobileNo) throws Exception;

    boolean validateOTP(int mobileNo, int otp) throws Exception;

}
