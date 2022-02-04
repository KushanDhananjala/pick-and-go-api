package edu.esoft.sdp.cw.pickandgoapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "otp_log")
@NoArgsConstructor
@AllArgsConstructor
public class OTPLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int mobileNo;
    private int otp;
    private LocalDateTime sentDateTime;
    private LocalDateTime expireDateTime;

}
