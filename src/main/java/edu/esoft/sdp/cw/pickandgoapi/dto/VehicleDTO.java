package edu.esoft.sdp.cw.pickandgoapi.dto;

import edu.esoft.sdp.cw.pickandgoapi.enums.VehicleType;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
public class VehicleDTO {
    @NotNull
    @Size(min = 4, max = 15)
    private String vehicleNo;
    private BigDecimal maxCapacity;
    private VehicleType vehicleType;
    private boolean availability;
}
