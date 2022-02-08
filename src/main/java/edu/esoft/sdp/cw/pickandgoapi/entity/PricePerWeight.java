package edu.esoft.sdp.cw.pickandgoapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "price_per_weight")
public class PricePerWeight {

    @Id
    private Long id;
    private BigDecimal firstFiveKgs;
    private BigDecimal forAdditionalKgs;

}
