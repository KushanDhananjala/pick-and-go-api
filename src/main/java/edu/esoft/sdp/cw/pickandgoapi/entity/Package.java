package edu.esoft.sdp.cw.pickandgoapi.entity;

import edu.esoft.sdp.cw.pickandgoapi.enums.PackageTypes;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "packages")
public class Package extends Auditable<Long> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double weight;
    private Double length;
    private Double width;
    private Double height;
    private BigDecimal price;
    private PackageTypes type;
    private int quantity;
    @ManyToOne
    @JoinColumn(name = "miscellaneousChargeId", referencedColumnName = "id")
    private MiscellaneousCharges miscellaneousChargeId;
}
