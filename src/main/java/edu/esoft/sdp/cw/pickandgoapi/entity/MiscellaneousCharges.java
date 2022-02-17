package edu.esoft.sdp.cw.pickandgoapi.entity;

import edu.esoft.sdp.cw.pickandgoapi.enums.MiscellaneousTypes;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "miscellaneous_charges")
@Data
@NoArgsConstructor
public class MiscellaneousCharges {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 40)
    private MiscellaneousTypes type;
    private BigDecimal price;

}
