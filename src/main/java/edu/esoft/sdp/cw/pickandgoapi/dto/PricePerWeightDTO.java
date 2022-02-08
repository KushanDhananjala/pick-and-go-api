package edu.esoft.sdp.cw.pickandgoapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PricePerWeightDTO {

    private Long id;
    private BigDecimal firstFiveKgs;
    private BigDecimal forAdditionalKgs;

}
