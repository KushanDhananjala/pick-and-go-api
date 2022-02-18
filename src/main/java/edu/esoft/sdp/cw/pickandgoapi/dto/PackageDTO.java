package edu.esoft.sdp.cw.pickandgoapi.dto;

import edu.esoft.sdp.cw.pickandgoapi.enums.MiscellaneousTypes;
import edu.esoft.sdp.cw.pickandgoapi.enums.PackageTypes;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PackageDTO {

    private Long id;
    private Double weight;
    private Double length;
    private Double width;
    private Double height;
    private BigDecimal price;
    private PackageTypes type;
    private int quantity;
    private MiscellaneousTypes category;

}
