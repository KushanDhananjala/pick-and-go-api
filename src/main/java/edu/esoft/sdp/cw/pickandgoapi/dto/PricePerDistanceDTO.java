package edu.esoft.sdp.cw.pickandgoapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PricePerDistanceDTO {

    private Long id;
    private BigDecimal firstFiveKms;
    private BigDecimal forAdditionalKms;

}
