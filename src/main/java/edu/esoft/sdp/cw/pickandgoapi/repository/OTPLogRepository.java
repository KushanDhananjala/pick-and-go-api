package edu.esoft.sdp.cw.pickandgoapi.repository;

import edu.esoft.sdp.cw.pickandgoapi.entity.OTPLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface OTPLogRepository extends JpaRepository<OTPLog, Long> {

    Optional<OTPLog> findFirstByMobileNoAndSentDateTimeIsBetweenOrderBySentDateTimeDesc(int mobileNo, LocalDateTime from, LocalDateTime to);

    Optional<OTPLog> findFirstByMobileNoAndOtpAndSentDateTimeIsBetweenOrderBySentDateTimeDesc(int mobileNo, int otp, LocalDateTime from, LocalDateTime to);

}
