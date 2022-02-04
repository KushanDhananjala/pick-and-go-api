package edu.esoft.sdp.cw.pickandgoapi.service.impl;

import edu.esoft.sdp.cw.pickandgoapi.dto.NotifySMSDTO;
import edu.esoft.sdp.cw.pickandgoapi.entity.OTPLog;
import edu.esoft.sdp.cw.pickandgoapi.exception.NotFoundException;
import edu.esoft.sdp.cw.pickandgoapi.repository.OTPLogRepository;
import edu.esoft.sdp.cw.pickandgoapi.service.OTPService;
import edu.esoft.sdp.cw.pickandgoapi.util.OTPGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class OTPServiceImpl implements OTPService {

    @Value("${notify.sms.user_id}")
    private Integer userId;
    @Value("${notify.sms.api_key}")
    private String apiKey;
    @Value("${notify.sms.sender_id}")
    private String senderId;
    @Value("${notify.sms.url}")
    private String url;

    private final OTPLogRepository otpLogRepository;

    @Override
    public boolean generateOTP(int mobileNo) throws Exception {

        int otp = sendOtp(mobileNo);

        OTPLog otpLog = new OTPLog(0L, mobileNo, otp, LocalDateTime.now(), LocalDateTime.now().plusMinutes(3));

        otpLogRepository.save(otpLog);

        return true;

    }

    @Override
    public boolean updateOTP(int mobileNo) throws Exception {

        Optional<OTPLog> optionalOTPLog = otpLogRepository.findFirstByMobileNoAndSentDateTimeIsBetweenOrderBySentDateTimeDesc(
                mobileNo, LocalDateTime.now().minusMinutes(2), LocalDateTime.now().plusMinutes(2));

        if (optionalOTPLog.isPresent()) {

            OTPLog otpLog = optionalOTPLog.get();

            otpLog.setOtp(sendOtp(mobileNo));
            otpLog.setSentDateTime(LocalDateTime.now());
            otpLog.setExpireDateTime(LocalDateTime.now().plusMinutes(3));

            otpLogRepository.save(otpLog);

            return true;
        }

        throw new NotFoundException("OTP not found for mobile number : " + mobileNo);


    }

    @Override
    public boolean validateOTP(int mobileNo, int otp) throws Exception {

        Optional<OTPLog> optionalOTPLog = otpLogRepository.findFirstByMobileNoAndOtpAndSentDateTimeIsBetweenOrderBySentDateTimeDesc(
                mobileNo, otp, LocalDateTime.now().minusMinutes(2), LocalDateTime.now().plusMinutes(2));

        return optionalOTPLog.isPresent();

    }

    private int sendOtp(int mobileNo) throws Exception {

        int randomOtp = OTPGenerator.generateRandomOtp();

        RestTemplate restTemplate = new RestTemplate();

        String msgBody = "Your verification code for Pick and Go Service is : " + randomOtp;

        NotifySMSDTO notifySMSDTO = new NotifySMSDTO(userId.longValue(), apiKey, senderId, Long.parseLong("94" + mobileNo), msgBody);

        restTemplate.postForEntity(url, notifySMSDTO, Object.class);

        return randomOtp;
    }
}
