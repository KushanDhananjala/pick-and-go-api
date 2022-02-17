package edu.esoft.sdp.cw.pickandgoapi.dto;

import edu.esoft.sdp.cw.pickandgoapi.enums.MiscellaneousTypes;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class MiscellaneousChargesDTO {

    private Long id;
    private MiscellaneousTypes type;
    private BigDecimal price;

}
