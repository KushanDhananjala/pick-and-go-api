package edu.esoft.sdp.cw.pickandgoapi.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "price_per_distance")
public class PricePerDistance {

    @Id
    private Long id;
    private BigDecimal firstFiveKms;
    private BigDecimal forAdditionalKms;

}
