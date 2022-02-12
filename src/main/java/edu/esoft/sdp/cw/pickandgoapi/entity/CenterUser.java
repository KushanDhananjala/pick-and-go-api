package edu.esoft.sdp.cw.pickandgoapi.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class CenterUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "center_id", referencedColumnName = "registrationId")
    private Center center;
    @OneToOne
    private User user;
    private String tempPw;
    private boolean isPwReset;

}
